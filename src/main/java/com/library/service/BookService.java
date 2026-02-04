package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest bookRequest);

    BookResponse getBookById(String bookId);

    List<BookResponse> getAllBooks();

    Page<BookResponse> getBooksByPage(Pageable pageable);

    BookResponse updateBook(String bookId, BookRequest bookRequest);

    void deleteBook(String bookId);
}
