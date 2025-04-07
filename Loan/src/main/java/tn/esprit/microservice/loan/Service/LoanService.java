package tn.esprit.microservice.loan.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.loan.Repositry.LoanRepositry;
import tn.esprit.microservice.loan.entity.Loan;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepositry loanRepositry;
    private static final int MAX_ACTIVE_LOANS = 3;
    public List<Loan> getAllLoans() {
        return loanRepositry.findAll();
    }

    public Optional<Loan> getLoanById(Integer loanId) {
        return loanRepositry.findById(loanId);
    }

    public Loan createLoan(Loan loan) {
        return loanRepositry.save(loan);
    }
    public void deleteLoan(Integer loanId) {
        loanRepositry.deleteById(loanId);
    }
    public Loan updateLoan(Integer loanId, Loan loanDetails) {
        Loan loan = loanRepositry.findById(loanId).get();
        loan.setBookId(loanDetails.getBookId());
        loan.setCardNumber(loanDetails.getCardNumber());
        loan.setLoanDate(loanDetails.getLoanDate());
        loan.setReturnDate(loanDetails.getReturnDate());
        return loanRepositry.save(loan);
    }

    public Loan createLoanWithValidation(Loan loanRequest) {
        List<Loan> activeLoans =  loanRepositry.findByCardNumberAndReturnDateIsNull(loanRequest.getCardNumber());

        if (activeLoans.size() >= MAX_ACTIVE_LOANS) {
            throw new IllegalStateException("Limite de " + MAX_ACTIVE_LOANS + " prêts actifs atteinte !");
        }

        return  loanRepositry.save(loanRequest);
    }


}

