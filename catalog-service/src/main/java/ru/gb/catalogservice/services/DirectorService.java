package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.DirectorDto;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.DirectorRepository;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final Sort SORT_DIRECTOR = Sort.by("lastName").ascending();

    public Director findById(Long id) {
        return directorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Режиссер с id=" + id + " не найден"));
    }

    public List<Director> findByFilter(String[] filterFirstName, String[] filterLastName) {
        return directorRepository.findAllByFirstNameInAndLastNameIn(filterFirstName, filterLastName);
    }

    public List<Director> findAll() {
        return directorRepository.findAll(SORT_DIRECTOR);
    }

    public ResultOperation directorAddInVideoteka(DirectorDto directorDto) {
        ResultOperation resultOperation = new ResultOperation();
        if (directorDto!=null && directorDto.getFirstName()!=null && directorDto.getLastName()!=null &&
            directorDto.getFirstName().length()>0 && directorDto.getLastName().length()>0){
            if (!isDirectorPresent(directorDto.getFirstName(),directorDto.getLastName())){
                Director director=new Director();
                director.setFirstName(directorDto.getFirstName());
                director.setLastName(directorDto.getLastName());
                directorRepository.save(director);
                resultOperation.setResult(true);
                resultOperation.setResultDescription("Режиссер добавлен");
                return resultOperation;
            }else {
                resultOperation.setResult(false);
                resultOperation.setResultDescription("Заданный режисеер уже существует");
                return resultOperation;
            }
        }else{
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Не заданы имя и/или фамилия нового режиссера");
            return resultOperation;
        }
    }

    private boolean isDirectorPresent(String firstName, String lastName){
        Director director = directorRepository.findByFirstNameAndLastName(firstName,lastName);
        return director!=null;
    }

}
