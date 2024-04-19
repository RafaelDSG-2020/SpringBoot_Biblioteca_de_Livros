package com.crud.cadastrousuario.domain.repository;


import com.crud.cadastrousuario.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);


    //List<Pessoa> findByPessoa(<List<Pessoa> pessoa, Pageable pageable);
}
