package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.api.controller.UserController;
import com.crud.cadastrousuario.domain.dto.AuthorDTO;

import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.AuthorRepository;
import com.crud.cadastrousuario.domain.repository.AuthorRepositorySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Mapper authorMapper;


    public static final Logger LOGGER = LoggerFactory.getLogger(CrudAuthorService.class);



    public List<AuthorDTO> findAuthor(Pageable pageable, AuthorDTO filter) {

        LOGGER.info("Executed the process of searching for author paged user in the database, paeable={} ", pageable);

        Page<Author> pageUser = authorRepository.findAll(
                AuthorRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Author> author = pageUser.getContent();

        return authorMapper.toDTO(author, AuthorDTO.class);
    }

    public AuthorDTO findAuthorByID(Long id) {

        LOGGER.info("Executed the process of searching for author by id in the database");

        isIdAvailable(id);
        Optional<Author> opt = authorRepository.findById(id);
        Author authorSave = opt.get();
        return authorMapper.toDTO(authorSave, AuthorDTO.class);
    }

    public AuthorDTO save(AuthorDTO authorCreateDTO) {

        LOGGER.info("Executed the process of saving author to the database");

       Author author = authorMapper.toEntity(authorCreateDTO,Author.class);
       Author authorSave = authorRepository.save(author);
       return authorMapper.toDTO(authorSave, AuthorDTO.class);

    }

    public AuthorDTO updateAuthorByID(Long id, AuthorDTO authorCreateDTO) {

        LOGGER.info("Executed the process of updating author by id in the database");
        Author author = authorMapper.toEntity(authorCreateDTO , Author.class );
        isIdAvailable(id);
        author.setId(id);
        Author authorSave = authorRepository.save(author);
        return  authorMapper.toDTO(authorSave, AuthorDTO.class);

    }

    public void deleteAuthorByID(Long id)  {

        LOGGER.info("Executed the process of delete author by id in the database");
        isIdAvailable(id);
        Optional<Author> opt = authorRepository.findById(id);
        Author author= opt.get();
        authorRepository.delete(author);
    }

    public void isIdAvailable(Long id) {

        LOGGER.info("Executed the process of validating author id in the database");
        Optional<Author> opt = authorRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }

    }
}
