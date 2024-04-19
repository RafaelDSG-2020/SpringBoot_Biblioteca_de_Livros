package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.UserRepository;
import com.crud.cadastrousuario.domain.repository.UserRepositorySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class CrudUserService {

    @Autowired
    public UserRepository userRepository;

    public User save(User user) throws BadRequestException {

        isEmailAvailable(user);
        return userRepository.save(user);
    }

    public List<User> findUser(Pageable pageable , UserDTO filter){

        Page<User> pageUser = userRepository.findAll(
                UserRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        return pageUser.getContent();

    }

    public User findUserByID(Long id) throws NotFoundException {
        isIdAvailable(id);
        Optional<User> opt = userRepository.findById(id);
        return opt.get();
    }

    public User updateUserByID(Long id, User user) throws NotFoundException, BadRequestException {

        isIdAvailable(id);
        isEmailAvailable(user);
        user.setId(id);
        return userRepository.save(user);


    }



    public void deletePeopleByID(Long id) throws NotFoundException, BadRequestException {
        isIdAvailable(id);
        User user = findUserByID(id);
        userRepository.delete(user);
    }

    public void isEmailAvailable(User user) throws  BadRequestException {

        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }

    public void isEmailAvailable(UserDTO userDTO) throws  BadRequestException {

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }



    public void isIdAvailable(Long id) throws NotFoundException {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }


    }


}
