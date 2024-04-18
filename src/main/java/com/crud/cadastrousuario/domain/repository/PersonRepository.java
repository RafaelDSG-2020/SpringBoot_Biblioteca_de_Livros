package com.crud.cadastrousuario.domain.repository;


import com.crud.cadastrousuario.domain.model.Person;
import com.crud.cadastrousuario.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    boolean existsByEmail(String email);


    //List<Pessoa> findByPessoa(<List<Pessoa> pessoa, Pageable pageable);
}
