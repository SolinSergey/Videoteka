package ru.gb.authorizationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.dto.*;
import ru.gb.authorizationservice.entities.PasswordChangeAttempt;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.exceptions.InputDataErrorException;
import ru.gb.authorizationservice.exceptions.NotDeletedUserException;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.PasswordChangeAttemptRepository;
import ru.gb.authorizationservice.repositories.UserRepository;
import ru.gb.authorizationservice.utils.Time;
import ru.gb.common.constants.Constant;
import ru.gb.common.constants.InfoMessage;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements InfoMessage, Constant {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordChangeAttemptRepository attemptRepository;
    private final RoleService roleService;
    private final InputValidationService validationService = new InputValidationService();
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private final Time time;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        List<User> found = userRepository.findByEmail(email);
        for (User user : found) {
            if (user.getEmail().equals(email) && !user.isDeleted()) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public StringResponse createNewUser(RegisterUserDto registerUserDto) {
        if (registerUserDto.getPassword()==null) {
            throw new InputDataErrorException(PASSWORD_CANNOT_BE_EMPTY);
        } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            throw new InputDataErrorException(PASSWORD_MISMATCH);
        } else {
            User user = new User();
            String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());

            saveAndNotify(registerUserDto, user, encryptedPassword);

            return new StringResponse("Пользователь с именем "
                    + registerUserDto.getUsername() + " успешно создан");
        }
    }

    public StringResponse restoreUser(RegisterUserDto registerUserDto, User user) {
        // восстанавливаем пользователя, если он есть в системе со статусом isDeleted = true
        if (!user.isDeleted()) {
            throw new NotDeletedUserException(USER_ALREADY_EXISTS);
        } else {
            if (registerUserDto.getPassword() == null) {
                throw new InputDataErrorException(PASSWORD_CANNOT_BE_EMPTY);
            } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                throw new InputDataErrorException(PASSWORD_MISMATCH);
            } else {
                String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());
                saveAndNotify(registerUserDto, user, encryptedPassword);
                return new StringResponse("Пользователь с именем "
                        + registerUserDto.getUsername() + " успешно восстановлен");
            }
        }
    }

    private void saveAndNotify(RegisterUserDto registerUserDto, User user, String encryptedPassword) {
        String validationMessage = validateAndSaveFields(registerUserDto, encryptedPassword, user);
        if (validationMessage != null) {
            throw new InputDataErrorException(validationMessage);
        }
        user.setDeleted(false);
        user.setDeletedBy(null);
        user.setDeletedWhen(null);
        userRepository.save(user);
        EmailDto emailDto = EmailDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .message("Поздравляем! Вы успешно зарегистрировались. \nВаш логин — " + user.getUsername())
                .subject(SIGN_UP)
                .build();

        rabbitSend(emailDto);
    }

    public void rabbitSend(EmailDto emailDto) {
        this.rabbitTemplate.setExchange(MAIL_EXCHANGE_NAME);
        this.rabbitTemplate.setRoutingKey(MAIL_ROUTE_KEY);
        Message message = null;
        try {
            message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(emailDto))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();

            message.getMessageProperties()
                    .setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,
                            MessageProperties.CONTENT_TYPE_JSON);

            this.rabbitTemplate.convertAndSend(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String validateAndSaveFields(RegisterUserDto registerUserDto, String encryptedPassword, User user) {
        user.setRole(roleService.getUserRole());

        String username = registerUserDto.getUsername();
        String email = registerUserDto.getEmail();
        String firstName = registerUserDto.getFirstName();
        String lastName = registerUserDto.getLastName();
        String phoneNumber = registerUserDto.getPhoneNumber();
        String address = registerUserDto.getAddress();
        String validationMessage = "";

        validationMessage = validationService.acceptableLogin(username);
        if (validationMessage.equals("")) {
            user.setUsername(username);
        } else {
            return validationMessage;
        }

        validationMessage = validationService.acceptablePassword(registerUserDto.getPassword());
        //  здесь берем пароль из ДТО, т.к. валидность проверяем у незашифрованного пароля, а в базу сохраняем
        //  encryptedPassword - зашифрованный пароль
        if (validationMessage.equals("")) {
            user.setPassword(encryptedPassword);
        } else {
            return validationMessage;
        }

        validationMessage = validationService.acceptableEmail(email);
        if (validationMessage.equals("")) {
            Optional<User> userByEmail = findByEmail(email);
            if (userByEmail.isPresent()) {
                return EMAIL_ALREADY_EXISTS;
            }
            user.setEmail(email);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptableFirstName(firstName);
        if (validationMessage.equals("")) {
            user.setFirstName(firstName);
        } else {
            return validationMessage;
        }


        validationMessage = validationService.acceptableLastName(lastName);
        if (validationMessage.equals("")) {
            user.setLastName(lastName);
        } else {
            return validationMessage;
        }


        if (phoneNumber==null || phoneNumber.isBlank()) {
            user.setPhoneNumber(null);
        } else {
            if (!validationService.acceptablePhoneNumber(phoneNumber)) {
                return INCORRECT_PHONE;
            }
            user.setPhoneNumber(phoneNumber);
        }

        if (address==null || address.isBlank()) {
            user.setAddress(null);
        } else {
            user.setAddress(address);
        }

        return null;    //  валидация прошла успешно
    }


    @Transactional
    public void setRoleToUser(Long changeUserId, String adminId, String role) {
        User user = userRepository.findById(changeUserId).orElseThrow
                (() -> new ResourceNotFoundException("Пользователь с id: " + changeUserId + " не найден"));
        user.setRole(roleService.getRoleByName(role).orElseThrow
                (() -> new ResourceNotFoundException("Роль " + role + " в базе не найдена")));
        User admin = findById(adminId).orElseThrow
                (() -> new ResourceNotFoundException("Aдмин с id: " + adminId + " не найден"));
        if (!admin.getRole().getTitle().equals("ROLE_ADMIN")) {
            throw new ResourceNotFoundException("Aдмин с id: " + adminId + " не найден");
        }
        user.setUpdateBy(findById(adminId).orElseThrow
                        (() -> new ResourceNotFoundException("Пользователь с id: " + adminId + " не найден"))
                .getUsername());
        userRepository.save(user);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(Long.valueOf(userId));
    }

    @Transactional
    public void safeDeleteById(Long deleteUserId, String adminId) {
        User u = userRepository.findById(deleteUserId).orElseThrow(() -> new ResourceNotFoundException
                ("Пользователь с id: " + deleteUserId + " не найден или удален"));

        if (!u.isDeleted()) {
            u.setDeleted(true);
            u.setDeletedBy(findById(adminId).orElseThrow(() -> new InputDataErrorException
                    ("Админ с id: " + adminId + " не найден")).getUsername());
            u.setDeletedWhen(LocalDateTime.now());
            userRepository.save(u);

        } else {
            throw new ResourceNotFoundException
                    ("Пользователь с id: " + deleteUserId + " не найден или удален");
        }
    }

    public List<User> findAllNotDeleted() {
        return userRepository.findAllNotDeleted();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public StringResponse fullNameById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow
                (() -> new ResourceNotFoundException(USER_NOT_FOUND));
        return new StringResponse(user.getFirstName().concat(" ").concat(user.getLastName()));
    }

    public List<ReviewWithNameDto> addingNamesToRatings(List<RatingDto> ratings) {
        String name;
        List<ReviewWithNameDto> result = new ArrayList<>(ratings.size());
        for (RatingDto rating : ratings) {
            Optional<User> user = findNotDeletedById(rating.getUser_id());
            name = user.map(value -> value.getFirstName().concat(" ")
                    .concat(value.getLastName())).orElse(USER_NOT_FOUND);
            ReviewWithNameDto newItem = ReviewWithNameDto.builder()
                    .user_id(rating.getUser_id())
                    .film_id(rating.getFilm_id())
                    .createDateTime(rating.getCreateDateTime())
                    .fullName(name)
                    .grade(rating.getGrade())
                    .review(rating.getReview())
                    .build();
            result.add(newItem);
        }
        return result;
    }

    private Optional<User> findNotDeletedById(Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent() && !result.get().isDeleted()) {
            return result;
        } else return Optional.empty();
    }

    public User findNameEmailById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Польователь с id="+id+" не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id="+id+" был удален");
        }
        return user;
    }


    @Transactional
    public void setPasswordChangeAttempt(String userId, String email) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() ->
                new ResourceNotFoundException("Польователь с id=" + userId + " не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id=" + userId + " не найден");
        }
        if (!user.getEmail().equals(email)) {
            throw new InputDataErrorException(INCORRECT_EMAIL);
        }

        String code =  composeVerificationLetter(user.getFirstName(), email);

        PasswordChangeAttempt attempt = attemptRepository.findById(user.getId())
                .orElse(new PasswordChangeAttempt());
        LocalDateTime createdWhen = time.now();
        attempt.setUser(user);
        attempt.setCreatedWhen(createdWhen);
        attempt.setCode(code);
        attempt.setVerified(false);
        attemptRepository.save(attempt);

    }

    private String composeVerificationLetter(String firstName, String email) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(email);
        emailDto.setFirstName(firstName);
        emailDto.setSubject("Код верификации");
        int random = (int) (Math.random()*1000000);
        String code = String.valueOf(random);
        while (code.length() < 6) {
            code = "0".concat(code);
        }
        emailDto.setMessage("Ваш код верификации — " + code);
        rabbitSend(emailDto);
        return code;
    }


    public StringResponse  checkCodeForPasswordChange(String userId, String code) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(
                () -> new ResourceNotFoundException("Польователь с id=" + userId + " не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id=" + userId + " не найден");
        } else {
            PasswordChangeAttempt attempt = attemptRepository.findById(user.getId()).orElseThrow(
                    () -> new InputDataErrorException(INCORRECT_CODE));
            if (!attempt.getCode().equals(code)) {
                attemptRepository.deleteById(attempt.getId());
                throw new InputDataErrorException(INCORRECT_CODE);
            }
            LocalDateTime expiredCodeTime = attempt.getCreatedWhen().plusMinutes(5);
            if (expiredCodeTime.isBefore(time.now())) {
                attemptRepository.delete(attempt);
                throw new InputDataErrorException(TIME_IS_UP);
            }
            attempt.setVerified(true);
            attemptRepository.save(attempt);
        }
        return new StringResponse(CORRECT_CODE);
    }


    public StringResponse updatePassword(String userId, String password, String confirmPassword) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(
                () -> new ResourceNotFoundException("Польователь с id=" + userId + " не найден"));
        if (user.isDeleted()) {
            throw new ResourceNotFoundException("Польователь с id=" + userId + " не найден");
        } else {
            PasswordChangeAttempt attempt = attemptRepository.findById(user.getId()).orElseThrow(
                    () -> new InputDataErrorException(TRY_AGAIN));
            if (!attempt.isVerified()) {
                attemptRepository.delete(attempt);
                throw new InputDataErrorException(TRY_AGAIN);
            } else {
                attemptRepository.delete(attempt);
                if (password==null || password.isBlank()) {
                    throw new InputDataErrorException(PASSWORD_CANNOT_BE_EMPTY);
                } else if (!password.equals(confirmPassword)) {
                    throw new InputDataErrorException(PASSWORD_MISMATCH);
                } else {
                    String encryptedPassword = passwordEncoder.encode(password);
                    String validationMessage = validationService.acceptablePassword(password);
                    if (validationMessage.equals("")) {
                        user.setPassword(encryptedPassword);
                        userRepository.save(user);
                        EmailDto emailDto = EmailDto.builder()
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .message(PASSWORD_UPDATED_SUCCESSFULLY)
                                .subject(PASSWORD_UPDATE)
                                .build();
                        rabbitSend(emailDto);
                    } else {
                        throw new InputDataErrorException(validationMessage);
                    }
                }
            }
        }
        return new StringResponse(PASSWORD_UPDATED_SUCCESSFULLY);
    }


}