package com.myminorproject.digitalLibrary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchAdminRequest {

    @NotBlank
    private String searchKey;

    @NotNull
    private String searchValue;
}
