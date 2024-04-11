package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.PessoaFilterDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.repository.PessoaRepository;
import com.crud.cadastrousuario.domain.repository.PessoaRepositorySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class CrudPessoaService {

    @Autowired
    public  PessoaRepository pessoaRepository;

    public Pessoa save(Pessoa pessoa) throws BadRequestException {

        isEmailAvailable(pessoa);
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> searchPeople(Pageable pageable , PessoaFilterDTO filter){

        Page<Pessoa> pageUser = pessoaRepository.findAll(
                PessoaRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        return pageUser.getContent();

    }

    public Pessoa searchPeopleofID(Long id) throws NotFoundException {
        isIdAvailable(id);
        Optional<Pessoa> opt = pessoaRepository.findById(id);
        return opt.get();
    }

    public Pessoa updatePeopleOFID(Long id, Pessoa pessoa) throws NotFoundException, BadRequestException {

        isIdAvailable(id);
        isEmailAvailable(pessoa);
        pessoa.setId(id);
        return pessoaRepository.save(pessoa);


    }

    public void deletePeopleOFID(Long id) throws NotFoundException, BadRequestException {
        isIdAvailable(id);
        Pessoa pessoa = searchPeopleofID(id);
        pessoaRepository.delete(pessoa);
    }

    public void isEmailAvailable(Pessoa pessoa) throws  BadRequestException {

        if (pessoaRepository.existsByEmail(pessoa.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }

    public void isIdAvailable(Long id) throws NotFoundException {
        Optional<Pessoa> opt = pessoaRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }


    }


}
