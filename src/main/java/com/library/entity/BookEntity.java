package com.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity extends BaseEntity {

    @Column(name = "book_id", nullable = false, unique = true)
    private String bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @PrePersist
    public void generateBookId() {
        if (this.bookId == null) {
            this.bookId = UUID.randomUUID().toString();
        }
    }
}
