package com.jac.Springbootlibrary.service;

import com.jac.Springbootlibrary.dao.BookRepository;
import com.jac.Springbootlibrary.dao.CheckoutRepository;
import com.jac.Springbootlibrary.entity.Book;
import com.jac.Springbootlibrary.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    // inject two repositories
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    // use constructor dependency injection to set up all our repositories
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        // get a book by its Id from database
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        // check if the book is not in our database, user already has it checked out  or other users have borrowed all the copies
        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }
        // if it exists and user has not borrowed yet, and there are still copies available, we add it to our book repository
        // and decrease its number of copies available by 1
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());
        // save the book into checkout repository
        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );
        checkoutRepository.save(checkout);

        return book.get();
    }
    // check if specified book is checked out by user
    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }
     // check how many books are in the return list of findBookByUserEmail();
    public int currentLoansCount(String userEmail){
        return checkoutRepository.findBookByUserEmail(userEmail).size();
    }
}
