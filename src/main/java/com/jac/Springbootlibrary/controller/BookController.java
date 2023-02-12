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
    public BookController(BookService bookService){
      this.bookService=bookService;
  }
 @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception{
      String userEmail ="testuser2@email.com";
      return bookService.checkoutBook(userEmail,bookId);

 }
}
