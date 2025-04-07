package tn.esprit.microservice.loan.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.loan.entity.Loan;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanRepositry extends JpaRepository<Loan,Integer> {
    List<Loan> findByCardNumberAndReturnDateIsNull(String cardNumber);
    List<Loan> findByBookId(Integer bookId);
    List<Loan> findByCardNumberContaining(String cardNumber);
    long countByReturnDateIsNull();

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.returnDate IS NOT NULL AND l.returnDate < :currentDate")
    long countByReturnDateBefore(@Param("currentDate") Date currentDate);

    @Query("SELECT l.bookId FROM Loan l GROUP BY l.bookId ORDER BY COUNT(l) DESC LIMIT 1")
    Integer findMostBorrowedBookId();
}