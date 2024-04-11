package com.crud.cadastrousuario.domain.dto.mapper;

import com.crud.cadastrousuario.domain.dto.PessoaCreateDTO;
import com.crud.cadastrousuario.domain.dto.PessoaResponseDTO;
import com.crud.cadastrousuario.domain.model.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PessoaMapper {

    @Autowired
    private ModelMapper mapper;

    public Pessoa toEntity(PessoaCreateDTO dto){
        Pessoa entity = mapper.map(dto, Pessoa.class);
        return entity;
    }

    public PessoaResponseDTO toDTO(Pessoa entity){
        PessoaResponseDTO dto = mapper.map(entity, PessoaResponseDTO.class);
        return dto;
    }

    public List<PessoaResponseDTO> toDTO(List<Pessoa> pessoas){

        return pessoas.stream()
                .map(pessoa -> toDTO(pessoa))
                .collect(Collectors.toList());
    }
}
