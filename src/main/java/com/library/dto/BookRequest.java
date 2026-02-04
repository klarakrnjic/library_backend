package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "Naslov knjige ne može biti prazan")
    private String title;

    @NotBlank(message = "Autor knjige ne može biti prazan")
    private String author;

    private String isbn;

    @Min(value = 1000, message = "Godina izdanja mora biti veća od 1000")
    private Integer publishedYear;

    @NotNull(message = "Dostupnost knjige nije navedena")
    private Boolean available;
}
