package tn.esprit.microservice.loan.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.loan.entity.Loan;

import java.util.List;

@Repository
public interface LoanRepositry extends JpaRepository<Loan,Integer> {
    List<Loan> findByCardNumberAndReturnDateIsNull(String cardNumber);
}
