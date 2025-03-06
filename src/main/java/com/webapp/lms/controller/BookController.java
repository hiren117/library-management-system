package com.webapp.lms.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webapp.lms.model.Book;
import com.webapp.lms.service.BookService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/books")
public class BookController { 
	@Autowired
	private BookService bookService;
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
	    if (book.getId() != null && bookService.exists(book.getId())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Prevent duplicate entry
	    }
	    Book savedBook = bookService.saveBook(book);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	
	@GetMapping
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}
	
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Long id,@RequestBody Book book) {
		return bookService.updateBook(id, book);
	}
	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "Book is Deleted Successfully";
	}
	

}
