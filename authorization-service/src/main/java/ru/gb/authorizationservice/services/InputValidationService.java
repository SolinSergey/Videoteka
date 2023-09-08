package ru.gb.authorizationservice.services;

import org.apache.commons.validator.routines.EmailValidator;
import ru.gb.common.constants.InfoMessage;

import java.util.ArrayList;

public class InputValidationService implements InfoMessage {
    private static ArrayList<Character> digits;
    private static ArrayList<Character> latin;
    private static ArrayList<Character> cyrillic;
    private static ArrayList<Character> nameCharacters;
    private static ArrayList<Character> phoneCharacters;
    private static ArrayList<Character> loginCharacters;
    private static ArrayList<Character> passwordCharacters;
    private static ArrayList<Character> emailCharacters;

    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MAX_LOGIN_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 32;


    public InputValidationService() {
        digits = new ArrayList<>(10);
        digits.add('0'); digits.add('1'); digits.add('2'); digits.add('3'); digits.add('4'); digits.add('5');
        digits.add('6'); digits.add('7'); digits.add('8'); digits.add('9');

        latin = new ArrayList<>(52);
        latin.add('A'); latin.add('B'); latin.add('C'); latin.add('D'); latin.add('E'); latin.add('F'); latin.add('G');
        latin.add('H'); latin.add('I'); latin.add('J'); latin.add('K'); latin.add('L'); latin.add('M'); latin.add('N');
        latin.add('O'); latin.add('P'); latin.add('Q'); latin.add('R'); latin.add('S'); latin.add('T'); latin.add('U');
        latin.add('V'); latin.add('W'); latin.add('X'); latin.add('Y'); latin.add('Z'); latin.add('a'); latin.add('b');
        latin.add('c'); latin.add('d'); latin.add('e'); latin.add('f'); latin.add('g'); latin.add('h'); latin.add('i');
        latin.add('j'); latin.add('k'); latin.add('l'); latin.add('m'); latin.add('n'); latin.add('o'); latin.add('p');
        latin.add('q'); latin.add('r'); latin.add('s'); latin.add('t'); latin.add('u'); latin.add('v'); latin.add('w');
        latin.add('x'); latin.add('y'); latin.add('z');

        cyrillic = new ArrayList<>(66);
        cyrillic.add('А'); cyrillic.add('Б'); cyrillic.add('В'); cyrillic.add('Г'); cyrillic.add('Д'); cyrillic.add('Е');
        cyrillic.add('Ё'); cyrillic.add('Ж'); cyrillic.add('З'); cyrillic.add('И'); cyrillic.add('Й'); cyrillic.add('К');
        cyrillic.add('Л'); cyrillic.add('М'); cyrillic.add('Н'); cyrillic.add('О'); cyrillic.add('П'); cyrillic.add('Р');
        cyrillic.add('С'); cyrillic.add('Т'); cyrillic.add('У'); cyrillic.add('Ф'); cyrillic.add('Х'); cyrillic.add('Ц');
        cyrillic.add('Ч'); cyrillic.add('Ш'); cyrillic.add('Щ'); cyrillic.add('Ъ'); cyrillic.add('Ы'); cyrillic.add('Ь');
        cyrillic.add('Э'); cyrillic.add('Ю'); cyrillic.add('Я'); cyrillic.add('а'); cyrillic.add('б'); cyrillic.add('в');
        cyrillic.add('г'); cyrillic.add('д'); cyrillic.add('е'); cyrillic.add('ё'); cyrillic.add('ж'); cyrillic.add('з');
        cyrillic.add('и'); cyrillic.add('й'); cyrillic.add('к'); cyrillic.add('л'); cyrillic.add('м'); cyrillic.add('н');
        cyrillic.add('о'); cyrillic.add('п'); cyrillic.add('р'); cyrillic.add('с'); cyrillic.add('т'); cyrillic.add('у');
        cyrillic.add('ф'); cyrillic.add('х'); cyrillic.add('ц'); cyrillic.add('ч'); cyrillic.add('ш'); cyrillic.add('щ');
        cyrillic.add('ъ'); cyrillic.add('ы'); cyrillic.add('ь'); cyrillic.add('э'); cyrillic.add('ю'); cyrillic.add('я');

        phoneCharacters = new ArrayList<>(14);
        phoneCharacters.addAll(digits);
        phoneCharacters.add('-'); phoneCharacters.add('('); phoneCharacters.add(')'); phoneCharacters.add('+');

        loginCharacters = new ArrayList<>(62);
        loginCharacters.addAll(digits);
        loginCharacters.addAll(latin);

        passwordCharacters = new ArrayList<>(128);
        passwordCharacters.addAll(digits);
        passwordCharacters.addAll(latin);
        passwordCharacters.addAll(cyrillic);

        nameCharacters = new ArrayList<>(121);
        nameCharacters.addAll(latin);
        nameCharacters.addAll(cyrillic);
        nameCharacters.add(' '); nameCharacters.add('-'); nameCharacters.add('\'');

        emailCharacters = new ArrayList<>(66);
        emailCharacters.addAll(digits);
        emailCharacters.addAll(latin);
        emailCharacters.add('@'); emailCharacters.add('.'); emailCharacters.add('_'); emailCharacters.add('-');
    }

    private static boolean areAllSymbolsInSet(String input, ArrayList<Character> pattern) {
        if (input==null || input.isBlank()) return true;
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (!pattern.contains(aChar)) return false;
        }
        return true;
    }

    public String acceptableEmail(String email)
    {
        if (email==null || email.isBlank()) return EMAIL_CANNOT_BE_EMPTY;
        if (!areAllSymbolsInSet(email, emailCharacters)) {
            return INVALID_EMAIL_CHARACTERS;
        } else {
            EmailValidator validator = EmailValidator.getInstance();
            return validator.isValid(email) ? "" : INCORRECT_EMAIL;
        }
    }

    public String acceptableLogin(String login)
    {
        if (login==null || login.isBlank()) return LOGIN_CANNOT_BE_EMPTY;
        if (login.length()<MIN_LOGIN_LENGTH) return "Минимальная длина логина должна быть " + MIN_LOGIN_LENGTH + " символа";
        if (login.length()>MAX_LOGIN_LENGTH) return "Максимальная длина логина " + MAX_LOGIN_LENGTH + " символов";
        if (!areAllSymbolsInSet(login, loginCharacters)) {
            return INVALID_LOGIN_CHARACTERS;
        } else {
            return "";
        }
    }

    public String acceptablePassword(String password)
    {
        if (password==null || password.isBlank()) return PASSWORD_CANNOT_BE_EMPTY;
        if (password.length()<MIN_PASSWORD_LENGTH) return "Минимальная длина пароля " + MIN_PASSWORD_LENGTH + " символов";
        if (password.length()>MAX_PASSWORD_LENGTH) return "Максимальная длина пароля " + MAX_PASSWORD_LENGTH + " символа";
        if (!areAllSymbolsInSet(password, passwordCharacters)) {
            return INVALID_PASSWORD_CHARACTERS;
        } else {
            return "";
        }
    }

    public String acceptableFirstName(String firstName) {
        if (firstName==null || firstName.isBlank()) return FIRSTNAME_CANNOT_BE_EMPTY;
        if (areAllSymbolsInSet(firstName, nameCharacters)) {
            return "";
        } else {
            return INVALID_FIRSTNAME;
        }
    }

    public String acceptableLastName(String lastName) {
        if (lastName==null || lastName.isBlank()) return LASTNAME_CANNOT_BE_EMPTY;
        if (areAllSymbolsInSet(lastName, nameCharacters)) {
            return "";
        } else {
            return INVALID_LASTNAME;
        }
    }


    public static boolean acceptablePhoneNumber(String phoneNumber) {
        if (!phoneNumber.isBlank()) {
            if (areAllSymbolsInSet(phoneNumber, phoneCharacters)) {
                phoneNumber = phoneNumber.replace(" ", "")
                        .replace("(", "").replace(")", "")
                        .replace("-", "").replace("+", "");
                int length = phoneNumber.length();
                return (length >= 7) && (length <= 13);        //  длина номера от 7 до 13
            } else {
                return false;
            }
        }
        return true;
    }

}
