package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.catalogservice.entities.Country;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.CountryRepository;
import ru.gb.catalogservice.utils.ResultOperation;
import ru.gb.common.constants.Constant;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService implements Constant {
    private final CountryRepository countryRepository;
    private final Sort SORT_COUNTRY = Sort.by(SORT_MODE_TITLE).ascending();
    public Country findById(Long id){
        return countryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Страна с id="+id+" не найдена"));
    }
    public List<Country> findByFilter(String[] strings){
        return countryRepository.findAllByTitleIsIn(strings);
    }
    public List<Country> findAll(){
        return countryRepository.findAll(SORT_COUNTRY);
    }

    public ResultOperation countryAddInVideoteka(CountryDto countryDto){
        ResultOperation resultOperation=new ResultOperation();
        if (countryDto!=null && countryDto.getTitle()!=null && countryDto.getTitle().length()>0){
            if (!isCountPresent(countryDto.getTitle())){
                Country country=new Country();
                country.setTitle(countryDto.getTitle());
                countryRepository.save(country);
                resultOperation.setResult(true);
                resultOperation.setResultDescription("Страна добавлена");
                return resultOperation;
            }else{
                resultOperation.setResult(false);
                resultOperation.setResultDescription("Заданная страна уже существует");
                return resultOperation;
            }
        }else{
            resultOperation.setResult(false);
            resultOperation.setResultDescription("Не задано название новой страны");
            return resultOperation;
        }
    }

    private boolean isCountPresent (String title){
        Country country=countryRepository.findByTitle(title);
        return country!=null;
    }
}
