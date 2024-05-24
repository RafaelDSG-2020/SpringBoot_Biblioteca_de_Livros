package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long>  {


    @Query(value = "SELECT * FROM (SELECT s.* FROM STOCK s WHERE s.BOOK_ID = :bookId ORDER BY s.ID) WHERE ROWNUM = 1", nativeQuery = true)
    Optional<Stock> findTopByBookId(@Param("bookId")Long bookID);
}
