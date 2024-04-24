package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;


import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.repository.AuthorRepository;
import com.crud.cadastrousuario.domain.repository.AuthorRepositorySpec;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CrudAuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public static final Logger LOGGER = LoggerFactory.getLogger(CrudAuthorService.class);



    public List<AuthorDTO> findAuthor(Pageable pageable, AuthorDTO filter) {

        log.info("Executed the process of searching for author paged user in the database, paeable={} ", pageable);

        Page<Author> pageUser = authorRepository.findAll(
                AuthorRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Author> authors = pageUser.getContent();

        return authors.stream()
                .map(author -> new AuthorDTO(author))
                .collect(Collectors.toList());


    }

    public AuthorDTO findAuthorByID(Long id) {

        log.info("Executed the process of searching for author by id in the database");

        Optional<Author> opt = isIdAvailable(id);
        Author authorSave = opt.get();
        return  new AuthorDTO(authorSave);

    }

    public AuthorDTO save(AuthorDTO authorCreateDTO) {

       log.info("Executed the process of saving author to the database");

        Author author = new Author(authorCreateDTO);
        author = authorRepository.save(author);
        return new AuthorDTO(author);

    }

    public AuthorDTO updateAuthorByID(Long id, AuthorDTO authorCreateDTO) {

        log.info("Executed the process of updating author by id in the database");

        Author author = new Author(authorCreateDTO);
        isIdAvailable(id);
        author.setId(id);
        author = authorRepository.save(author);
        return new AuthorDTO(author);

    }

    public void deleteAuthorByID(Long id)  {

        log.info("Executed the process of delete author by id in the database");

        Optional<Author> opt = isIdAvailable(id);
        Author author= opt.get();
        authorRepository.delete(author);
    }

    public Optional<Author> isIdAvailable(Long id) {

        log.info("Executed the process of validating author id in the database");
        Optional<Author> opt = authorRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Author with id: " + id + " does not exist.");
        }

        return  opt;

    }
}
