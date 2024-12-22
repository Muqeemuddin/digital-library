package com.myminorproject.digitalLibrary.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchRequest {

    private String searchKey;

    private String searchValue;


}
