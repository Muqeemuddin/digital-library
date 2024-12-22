package com.myminorproject.digitalLibrary.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRequest {

    private String deleteKey;

    private String deleteValue;
}
