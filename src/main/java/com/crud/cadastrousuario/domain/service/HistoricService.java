package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.HistoricDTO;
import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.model.Historic;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import com.crud.cadastrousuario.domain.repository.HistoricRepository;
import com.crud.cadastrousuario.domain.repository.LoanOfBooksRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Log4j2
@Service
public class HistoricService {


    @Autowired
    private LoanOfBooksRepository loanOfBooksRepository;

    @Autowired
    private HistoricRepository historicRepository;

    private static final double AMOUNT_OF_THE_FINE_WITHOUT_DELAY = 0.0;

    private static final double FINE_RATE_DAYS = 2.15;


    public HistoricDTO saveHistoricLoan(Long loanID) {


        LoanOfBooks loan = loanOfBooksRepository.findById(loanID).orElseThrow(()
                -> new BadRequestException("Loan is not Exist"));

        Historic historic = new Historic(loan);
        historic.setDateReturnUser(LocalDate.of(2024, 6, 5));
        fineCalculad(historic);
        historic = historicRepository.save(historic);


        loanOfBooksRepository.delete(loan);
        return new HistoricDTO(historic);




    }

    private static void fineCalculad(Historic historic) {

        long fineBook = ChronoUnit.DAYS.between(historic.getDateReturnUser() , historic.getDateReturnExpected() );
        historic.setFineBook(fineBook < 0 ? (fineBook * -FINE_RATE_DAYS) : AMOUNT_OF_THE_FINE_WITHOUT_DELAY);
    }
}
