package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;

import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.repository.AuthorRepository;
import com.crud.cadastrousuario.domain.repository.AuthorRepositorySpec;

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

    @Autowired
    CrudUserService personService;



    public List<Author> findByAuthor(Pageable pageable, AuthorDTO filter) {

        Page<Author> pageUser = authorRepository.findAll(
                AuthorRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Author> author = pageUser.getContent();

        return authorMapper.toDTO(author, Author.class);
    }

    public Author findAuthorByID(Long id) {

        personService.isIdAvailable(id);
        Optional<Author> opt = authorRepository.findById(id);
        Author authorSave = opt.get();
        return authorMapper.toDTO(authorSave, Author.class);
    }

    public Author save(AuthorDTO authorCreateDTO) {

        Author author = authorMapper.toEntity(authorCreateDTO,Author.class);
        Author authorSave = authorRepository.save(author);
       return authorMapper.toDTO(authorSave, Author.class);
    }
}
