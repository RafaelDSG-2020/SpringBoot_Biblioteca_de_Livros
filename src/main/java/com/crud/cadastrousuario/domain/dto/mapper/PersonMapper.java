package com.crud.cadastrousuario.domain.dto.mapper;

import com.crud.cadastrousuario.domain.dto.PersonCreateDTO;
import com.crud.cadastrousuario.domain.dto.PersonResponseDTO;
import com.crud.cadastrousuario.domain.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    @Autowired
    private ModelMapper mapper;

//    public Person toEntity(PersonCreateDTO dto){
//        Person entity = mapper.map(dto, Person.class);
//        return entity;
//    }

    public <R> R toEntity(Object source, Class<R> resultClass) {
        return mapper.map(source, resultClass);
    }


    public <R> R toDTO (Object entity, Class<R> resultClass){
        return mapper.map(entity, resultClass);
    }
//    public PersonResponseDTO toDTO(Person entity){
//        PersonResponseDTO dto = mapper.map(entity, PersonResponseDTO.class);
//        return dto;
//    }


    public <S, T> List<T> toDtoList(List<S> sourceList, Class<T> dtoClass) {
        return sourceList.stream()
                .map(source -> toDTO(source, dtoClass))
                .collect(Collectors.toList());
    }



//    public List<PersonResponseDTO> toDTO(List<Person> pessoas){
//
//        return pessoas.stream()
//                .map(pessoa -> toDTO(pessoa))
//                .collect(Collectors.toList());
//    }
}
