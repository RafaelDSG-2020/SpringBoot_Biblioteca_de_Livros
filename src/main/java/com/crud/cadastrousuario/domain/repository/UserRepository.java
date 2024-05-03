package com.crud.cadastrousuario.domain.repository;


import com.crud.cadastrousuario.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByFlag(Integer flag);

    Optional<User> findByIdAndFlagEquals(Long id, Integer flag);


    //List<Pessoa> findByPessoa(<List<Pessoa> pessoa, Pageable pageable);
}
