package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Historic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoricRepository extends JpaRepository<Historic, Long> , JpaSpecificationExecutor<Historic> {
}
