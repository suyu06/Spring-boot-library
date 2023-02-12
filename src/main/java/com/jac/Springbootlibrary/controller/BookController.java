package com.jac.Springbootlibrary.controller;


import com.jac.Springbootlibrary.entity.Book;
import com.jac.Springbootlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/books")
public class BookController {
    // inject BookService
    private BookService bookService;

    //
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
   @GetMapping("/secure/currentloans/count")
   public int currentLoansCount(){
        String userEmail = "testuser2@email.com";
        return bookService.currentLoansCount(userEmail);

   }
    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestParam Long bookId){
        String  userEmail = "testuser2@email.com";
        return bookService.checkoutBookByUser(userEmail,bookId);
    }
    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception {
        String userEmail = "testuser2@email.com";
        return bookService.checkoutBook(userEmail, bookId);

    }


}
