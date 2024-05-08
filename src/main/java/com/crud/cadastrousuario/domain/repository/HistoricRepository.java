package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Historic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricRepository extends JpaRepository<Historic, Long> {
}
