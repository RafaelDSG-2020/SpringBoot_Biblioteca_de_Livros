package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.api.controller.UserController;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.UserRepository;
import com.crud.cadastrousuario.domain.repository.UserRepositorySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private Mapper userMapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(CrudUserService.class);

    public UserDTO save(UserDTO userCreateDTO) {

        LOGGER.info("Executed the process of saving user to the database");
        User user = userMapper.toEntity(userCreateDTO, User.class);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        User userSave = userRepository.save(user);
        return userMapper.toDTO(userSave, UserDTO.class);

    }




    public List<UserDTO> findUser(Pageable pageable , UserDTO filter) {

        LOGGER.info("Executed the process of searching for user paged user in the database, paeable={} ", pageable);
        Page<User> pageUser = userRepository.findAll(
                UserRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<User> user = pageUser.getContent();

        return userMapper.toDTO(user, UserDTO.class);

    }

    public UserDTO findUserByID(Long id) {

        LOGGER.info("Executed the process of searching for user by id in the database");
        isIdAvailable(id);
        Optional<User> opt = userRepository.findById(id);
        User userSave = opt.get();

        return userMapper.toDTO(userSave, UserDTO.class);

    }

    public UserDTO updateUserByID(Long id, UserDTO userCreateDTO)  {

        LOGGER.info("Executed the process of updating user by id in the database");
        User user = userMapper.toEntity(userCreateDTO , User.class );
        isIdAvailable(id);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        user.setId(id);
        User userSave = userRepository.save(user);
        return  userMapper.toDTO(userSave, UserDTO.class);


    }


    public void deleteUserByID(Long id)  {

        LOGGER.info("Executed the process of delete user by id in the database");
        isIdAvailable(id);
        Optional<User> opt = userRepository.findById(id);
        User user= opt.get();
       // User user = findUserByID(id);
        userRepository.delete(user);
    }

    public void isEmailAvailable(User user) {

        LOGGER.info("Executed the process of validating user email in the database");
        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }


    public void isIdAvailable(Long id) {

        LOGGER.info("Executed the process of validating user id in the database");
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }

    }

    private void isPhoneAvailable(User user) {

        LOGGER.info("Executed the process of validating user phone numbers in the database");
        if (userRepository.existsByPhone(user.getPhone())){
            throw new BadRequestException("Pessoa com Telefone cadastrado");
        }
    }


}
