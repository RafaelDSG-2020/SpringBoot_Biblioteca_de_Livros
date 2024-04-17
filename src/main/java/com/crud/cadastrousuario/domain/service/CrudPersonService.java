package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.PersonDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Person;
import com.crud.cadastrousuario.domain.repository.PersonRepository;
import com.crud.cadastrousuario.domain.repository.PersonRepositorySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class CrudPersonService {

    @Autowired
    public PersonRepository pessoaRepository;

    public Person save(Person pessoa) throws BadRequestException {

        isEmailAvailable(pessoa);
        return pessoaRepository.save(pessoa);
    }

    public List<Person> searchPeople(Pageable pageable , PersonDTO filter){

        Page<Person> pageUser = pessoaRepository.findAll(
                PersonRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        return pageUser.getContent();

    }

    public Person searchPeopleByID(Long id) throws NotFoundException {
        isIdAvailable(id);
        Optional<Person> opt = pessoaRepository.findById(id);
        return opt.get();
    }

    public Person updatePeopleByID(Long id, Person pessoa) throws NotFoundException, BadRequestException {

        isIdAvailable(id);
        isEmailAvailable(pessoa);
        pessoa.setId(id);
        return pessoaRepository.save(pessoa);


    }

    public Person updatePeopleByIDD(Long id) throws NotFoundException, BadRequestException {


        isIdAvailable(id);
        Person pessoa = new Person();
        isEmailAvailable(pessoa);
        pessoa.setId(id);
        return pessoaRepository.save(pessoa);


    }



    public void deletePeopleByID(Long id) throws NotFoundException, BadRequestException {
        isIdAvailable(id);
        Person pessoa = searchPeopleByID(id);
        pessoaRepository.delete(pessoa);
    }

    public void isEmailAvailable(Person pessoa) throws  BadRequestException {

        if (pessoaRepository.existsByEmail(pessoa.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }

    public void isEmailAvailable(PersonDTO pessoa) throws  BadRequestException {

        if (pessoaRepository.existsByEmail(pessoa.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }



    public void isIdAvailable(Long id) throws NotFoundException {
        Optional<Person> opt = pessoaRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }


    }


}
