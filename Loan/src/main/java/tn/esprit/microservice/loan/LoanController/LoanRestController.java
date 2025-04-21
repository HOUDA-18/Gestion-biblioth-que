package tn.esprit.microservice.loan.LoanController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.loan.Service.LoanService;
import tn.esprit.microservice.loan.dto.Book;
import tn.esprit.microservice.loan.entity.Loan;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/filter")
    public List<Loan> filterLoans(
            @RequestParam(required = false) Integer loanId,
            @RequestParam(required = false) Integer bookId,
            @RequestParam(required = false) String cardNumber) {

        return loanService.getLoansByFilters(loanId, bookId, cardNumber);
    }

    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return loanService.getLoanStatistics();
    }

    @GetMapping("/{loanId}/adjust-return-date")
    public ResponseEntity<String> adjustReturnDateForHoliday(
            @PathVariable Integer loanId,
            @RequestParam String countryCode) {

        Loan updatedLoan = loanService.checkAndAdjustReturnDate(loanId, countryCode);

        if (updatedLoan.getReturnDate().after(loanService.getLoanById(loanId).get().getReturnDate())) {
            return ResponseEntity.ok("Date étendue au " + updatedLoan.getReturnDate());
        }

        return ResponseEntity.ok("Date de retour valide : " + updatedLoan.getReturnDate());
    }

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage;
    }

    @RequestMapping("/books")
    public List<Book> fetchAvailableBooks() {
        return loanService.fetchAvailableBooks();
    }
}

