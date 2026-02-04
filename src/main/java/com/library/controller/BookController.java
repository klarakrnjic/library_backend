package com.library.controller;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info("POST zahtjev: Kreiranje nove knjige");
        BookResponse response = bookService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable String bookId) {
        log.info("GET zahtjev: Dohvaćanje knjige s ID-om: {}", bookId);
        BookResponse response = bookService.getBookById(bookId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        log.info("GET zahtjev: Dohvaćanje knjiga");

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            Page<BookResponse> response = bookService.getBooksByPage(pageable);
            return ResponseEntity.ok(response);
        } else {
            List<BookResponse> response = bookService.getAllBooks();
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable String bookId,
            @Valid @RequestBody BookRequest bookRequest) {
        log.info("PUT zahtjev: Ažuriranje knjige s ID-om: {}", bookId);
        BookResponse response = bookService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        log.info("DELETE zahtjev: Brisanje knjige s ID-om: {}", bookId);
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
