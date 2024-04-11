package com.crud.cadastrousuario.domain.repository;


import com.crud.cadastrousuario.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {
    boolean existsByEmail(String email);


    //List<Pessoa> findByPessoa(<List<Pessoa> pessoa, Pageable pageable);
}
