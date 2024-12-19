package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    public Admin to(){
        return Admin.builder().name(this.name).email(this.email).build();
    }
}
