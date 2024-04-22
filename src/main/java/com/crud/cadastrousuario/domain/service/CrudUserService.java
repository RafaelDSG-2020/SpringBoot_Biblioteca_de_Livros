package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
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

    @Autowired
    private Mapper userMapper;

    public User save(UserDTO userCreateDTO) {

        User user = userMapper.toEntity(userCreateDTO, User.class);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        User userSave = userRepository.save(user);
        return userMapper.toDTO(userSave, User.class);

    }




    public List<User> findUser(Pageable pageable , UserDTO filter){

        Page<User> pageUser = userRepository.findAll(
                UserRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<User> user = pageUser.getContent();

        return userMapper.toDTO(user, User.class);

    }

    public User findUserByID(Long id) throws NotFoundException {
        isIdAvailable(id);
        Optional<User> opt = userRepository.findById(id);
        User userSave = opt.get();

        return userMapper.toDTO(userSave, User.class);

    }

    public User updateUserByID(Long id, UserDTO userCreateDTO)  {

        User user = userMapper.toEntity(userCreateDTO , User.class );
        isIdAvailable(id);
        isEmailAvailable(user);
        isPhoneAvailable(user);
        user.setId(id);
        User userSave = userRepository.save(user);
        return  userMapper.toDTO(userSave, User.class);


    }



    public void deletePeopleByID(Long id)  {
        isIdAvailable(id);
        User user = findUserByID(id);
        userRepository.delete(user);
    }

    public void isEmailAvailable(User user) {

        if (userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Pessoa com email cadastrado");
        }

    }


    public void isIdAvailable(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Pessoa com id: " + id + " Inexistente.");
        }

    }

    private void isPhoneAvailable(User user) {

        if (userRepository.existsByPhone(user.getPhone())){
            throw new BadRequestException("Pessoa com Telefone cadastrado");
        }
    }


}
