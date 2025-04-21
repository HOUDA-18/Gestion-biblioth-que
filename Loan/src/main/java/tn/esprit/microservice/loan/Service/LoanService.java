package tn.esprit.microservice.loan.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.loan.Repositry.LoanRepositry;
import tn.esprit.microservice.loan.dto.Book;
import tn.esprit.microservice.loan.entity.Loan;
import tn.esprit.microservice.loan.proxy.BookClient;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepositry loanRepositry;
    @Autowired
    private HolidayCheckService holidayCheckService;
    @Autowired
    private BookClient bookServiceClient;
    private static final int MAX_ACTIVE_LOANS = 3;
    public List<Book> fetchAvailableBooks() {
        return bookServiceClient.getAvailableBooks();
    }

    public void createLoan(Integer bookId, String cardNumber) {
        // 1. Récupérer les livres disponibles depuis le service Book
        List<Book> availableBooks = fetchAvailableBooks();

        // 2. Vérifier que le livre existe et est disponible
        Optional<Book> matchedBook = availableBooks.stream()
                .filter(book -> book.getBookId().equals(bookId))
                .findFirst();

        if (matchedBook.isEmpty()) {
            throw new RuntimeException("Livre indisponible pour le prêt ou introuvable.");
        }

        // 3. Créer et enregistrer le prêt
        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setCardNumber(cardNumber);
        loan.setLoanDate(new Date());

        // Calcul de la date de retour (ex: 2 semaines plus tard)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        loan.setReturnDate(calendar.getTime());

        loanRepositry.save(loan);
    }

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
        List<Loan> activeLoans = loanRepositry.findByCardNumberAndReturnDateIsNull(loanRequest.getCardNumber());

        if (activeLoans.size() >= MAX_ACTIVE_LOANS) {
            throw new IllegalStateException("Limite de " + MAX_ACTIVE_LOANS + " prêts actifs atteinte !");
        }

        return loanRepositry.save(loanRequest);
    }

    public List<Loan> getLoansByFilters(Integer loanId, Integer bookId, String cardNumber) {
        List<Loan> filteredLoans = new ArrayList<>();

        // Si aucun filtre n'est spécifié, retourner tous les prêts
        if (loanId == null && bookId == null && cardNumber == null) {
            return getAllLoans();
        }

        // Appliquer les filtres un par un
        if (loanId != null) {
            Optional<Loan> loan = loanRepositry.findById(loanId);
            loan.ifPresent(filteredLoans::add);
        }

        if (bookId != null) {
            List<Loan> loansByBook = (filteredLoans.isEmpty()) ?
                    loanRepositry.findByBookId(bookId) :
                    filteredLoans.stream()
                            .filter(loan -> loan.getBookId().equals(bookId))
                            .collect(Collectors.toList());
            filteredLoans = loansByBook;
        }

        if (cardNumber != null && !cardNumber.isEmpty()) {
            List<Loan> loansByCard = (filteredLoans.isEmpty()) ?
                    loanRepositry.findByCardNumberContaining(cardNumber) :
                    filteredLoans.stream()
                            .filter(loan -> loan.getCardNumber().contains(cardNumber))
                            .collect(Collectors.toList());
            filteredLoans = loansByCard;
        }

        return filteredLoans;
    }


    public Map<String, Object> getLoanStatistics() {
        return Map.of(
                "totalLoans", loanRepositry.count(),
                "activeLoans", loanRepositry.countByReturnDateIsNull(),
                "overdueLoans", loanRepositry.countByReturnDateBefore(new Date()),
                "mostBorrowedBookId", loanRepositry.findMostBorrowedBookId()
        );
    }
    @Transactional
    public Loan checkAndAdjustReturnDate(Integer loanId, String countryCode) {
        Loan loan = loanRepositry.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));

        if (holidayCheckService.isPublicHoliday(loan.getReturnDate(), countryCode)) {
            Date newDate = addDays(loan.getReturnDate(), 1);
            loan.setReturnDate(newDate);
            return loanRepositry.save(loan);
        }

        return loan;
    }

    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
