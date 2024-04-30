package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long>  {
}
