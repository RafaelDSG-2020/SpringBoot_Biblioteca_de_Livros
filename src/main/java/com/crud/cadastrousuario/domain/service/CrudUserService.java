package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.UserDTO;

import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.UserRepository;
import com.crud.cadastrousuario.domain.repository.UserRepositorySpec;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CrudUserService {

    @Autowired
    public UserRepository userRepository;

    private final Integer STATUS_FLAG_ACTIVE = 1;

    private final Integer STATUS_FLAG_INACTIVE = 0;




    public List<UserDTO> findUser(Pageable pageable , UserDTO filter) {

        log.info("Executed the process of searching for user paged user in the database, paeable={} ", pageable);
        Page<User> pageUser = userRepository.findAll(
                UserRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<User> users = pageUser.getContent();

        return users.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());


    }

    public UserDTO findUserByIDActive(Long id) {

        log.info("Executed the process of searching for user by id active in the database");
        User userSave = isIdAndFlagActive(id);
        return new UserDTO(userSave);

    }


    public UserDTO save(UserDTO userCreateDTO) {

        log.info("Executed the process of saving user to the database");

        User user = new User(userCreateDTO);
        isEmailAvailable(user);
        user = userRepository.save(user);
        return new UserDTO(user);

    }


    public UserDTO updateUserByID(Long id, UserDTO userCreateDTO)  {

        log.info("Executed the process of updating user by id in the database");
        User user = new User(userCreateDTO);
        isIdAvailable(id);
        isEmailAvailable(user);
        user.setId(id);
        user = userRepository.save(user);

        return new UserDTO(user);

    }


    public void deleteUserByID(Long id)  {

        log.info("Executed the process of delete user by id in the database");

        User userSave = isIdAndFlagActive(id);
        userSave.setFlag(STATUS_FLAG_INACTIVE);
        userRepository.save(userSave);
    }

    public void isEmailAvailable(User user) {

        log.info("Executed the process of validating user email in the database");

        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("User with registered email");
        }

    }


    public User isIdAvailable(Long id) {

        log.info("Executed the process of validating user id in the database");

        return userRepository.findById(id )
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " does not exist or Flag inactive."));


    }

    public User isIdAndFlagActive(Long id) {

        log.info("Executed the process of validating user id  and flag Active in the database");

        return userRepository.findByIdAndFlagEquals(id , STATUS_FLAG_ACTIVE)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " does not exist or Flag inactive."));


    }



}
