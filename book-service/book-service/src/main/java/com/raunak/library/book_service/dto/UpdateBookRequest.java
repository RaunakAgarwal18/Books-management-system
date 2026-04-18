package com.raunak.library.book_service.dto;

import lombok.Data;

@Data
public class UpdateBookRequest {

    private String title;
    private String author;
    private String description;
    private String category;
    private String img;

    private Integer copies;
    private String isbn;
}