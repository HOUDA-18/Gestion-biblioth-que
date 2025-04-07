package tn.esprit.microservice.loan.LoanController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.loan.Service.LoanService;
import tn.esprit.microservice.loan.entity.Loan;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan")
public class LoanRestController {
    @Autowired
    private LoanService loanService;

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }
    @PostMapping("/secure")
    public ResponseEntity<Loan> createLoanWithValidation(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.createLoanWithValidation(loan));
    }
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }
    @GetMapping("/{loanId}")
    public Optional<Loan> getLoanById(@PathVariable Integer loanId) {
        return loanService.getLoanById(loanId);
    }
    @PutMapping("/{loanId}")
    public Loan updateLoan(@PathVariable Integer loanId, @RequestBody Loan loanDetails) {
        return loanService.updateLoan(loanId, loanDetails);
    }

    @DeleteMapping("/{loanId}")
    public void deleteLoan(@PathVariable Integer loanId) {
        loanService.deleteLoan(loanId);
    }

}
