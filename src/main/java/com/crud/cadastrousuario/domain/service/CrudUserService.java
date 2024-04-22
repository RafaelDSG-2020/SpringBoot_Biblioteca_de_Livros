package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.api.controller.UserController;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
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

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public User save(UserDTO userCreateDTO) {

        LOGGER.info("START OF THE SAVE METHOD IN SERVICE");
        User user = userMapper.toEntity(userCreateDTO, User.class);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        User userSave = userRepository.save(user);
        return userMapper.toDTO(userSave, User.class);

    }




    public List<User> findUser(Pageable pageable , UserDTO filter){

        LOGGER.info("START OF THE FINDUSER METHOD IN SERVICE");
        Page<User> pageUser = userRepository.findAll(
                UserRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<User> user = pageUser.getContent();

        return userMapper.toDTO(user, User.class);

    }

    public User findUserByID(Long id) throws NotFoundException {

        LOGGER.info("START OF THE FINDUSERBYID METHOD IN SERVICE");
        isIdAvailable(id);
        Optional<User> opt = userRepository.findById(id);
        User userSave = opt.get();

        return userMapper.toDTO(userSave, User.class);

    }

    public User updateUserByID(Long id, UserDTO userCreateDTO)  {

        LOGGER.info("START OF THE UPDATEUSERBYID METHOD IN SERVICE");
        User user = userMapper.toEntity(userCreateDTO , User.class );
        isIdAvailable(id);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        user.setId(id);
        User userSave = userRepository.save(user);
        return  userMapper.toDTO(userSave, User.class);


    }


    public void deletePeopleByID(Long id)  {

        LOGGER.info("START OF THE DELETEPEOPLEBYID METHOD IN SERVICE");
        isIdAvailable(id);
        User user = findUserByID(id);
        userRepository.delete(user);
    }

    public void isEmailAvailable(User user) {

        LOGGER.info("START OF THE ISEMAILAVAILABLE METHOD IN SERVICE");
        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }


    public void isIdAvailable(Long id) {

        LOGGER.info("START OF THE ISIDAVAILABLE METHOD IN SERVICE");
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }

    }

    private void isPhoneAvailable(User user) {

        LOGGER.info("START OF THE ISPHONEAVAILABLE METHOD IN SERVICE");
        if (userRepository.existsByPhone(user.getPhone())){
            throw new BadRequestException("Pessoa com Telefone cadastrado");
        }
    }


}
