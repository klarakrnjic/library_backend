package com.library.repository;

import com.library.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByBookId(String bookId);

    Optional<BookEntity> findByIsbn(String isbn);

    Page<BookEntity> findAll(Pageable pageable);
}
