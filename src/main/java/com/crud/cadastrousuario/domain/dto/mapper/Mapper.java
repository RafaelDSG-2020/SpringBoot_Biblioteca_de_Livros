package com.crud.cadastrousuario.domain.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

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


    public <S, T> List<T> toDTO(List<S> sourceList, Class<T> dtoClass) {
        return sourceList.stream()
                .map(source -> toDTO(source, dtoClass))
                .collect(Collectors.toList());
    }


}
