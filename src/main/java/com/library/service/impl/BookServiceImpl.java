package com.library.service.impl;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.BookEntity;
import com.library.exception.BookNotFoundException;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        log.info("Kreiram novu knjigu: {}", bookRequest.getTitle());

        BookEntity book = BookEntity.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .isbn(bookRequest.getIsbn())
                .publishedYear(bookRequest.getPublishedYear())
                .available(bookRequest.getAvailable())
                .build();

        BookEntity savedBook = bookRepository.save(book);
        log.info("Knjiga uspješno kreirana s ID-om: {}", savedBook.getBookId());

        return mapToResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(String bookId) {
        log.info("Dohvaćam knjigu s ID-om: {}", bookId);

        BookEntity book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> {
                    log.error("Knjiga nije pronađena s ID-om: {}", bookId);
                    return new BookNotFoundException("Knjiga nije pronađena s ID-om: " + bookId);
                });

        return mapToResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        log.info("Dohvaćam sve knjige");

        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> getBooksByPage(Pageable pageable) {
        log.info("Dohvaćam knjige sa paginacijom: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());

        return bookRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public BookResponse updateBook(String bookId, BookRequest bookRequest) {
        log.info("Ažuriram knjigu s ID-om: {}", bookId);

        BookEntity book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> {
                    log.error("Knjiga nije pronađena za ažuriranje s ID-om: {}", bookId);
                    return new BookNotFoundException("Knjiga nije pronađena s ID-om: " + bookId);
                });

        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublishedYear(bookRequest.getPublishedYear());
        book.setAvailable(bookRequest.getAvailable());

        BookEntity updatedBook = bookRepository.save(book);
        log.info("Knjiga uspješno ažurirana s ID-om: {}", bookId);

        return mapToResponse(updatedBook);
    }

    @Override
    public void deleteBook(String bookId) {
        log.info("Brišem knjigu s ID-om: {}", bookId);

        BookEntity book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> {
                    log.error("Knjiga nije pronađena za brisanje s ID-om: {}", bookId);
                    return new BookNotFoundException("Knjiga nije pronađena s ID-om: " + bookId);
                });

        bookRepository.delete(book);
        log.info("Knjiga uspješno obrisana s ID-om: {}", bookId);
    }

    private BookResponse mapToResponse(BookEntity book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedYear(book.getPublishedYear())
                .available(book.getAvailable())
                .build();
    }
}
