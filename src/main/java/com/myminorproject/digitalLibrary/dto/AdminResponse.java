package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Admin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class AdminResponse {
    @NotNull
    private Integer id;
    @NotNull
    private String adminName;
    @NotNull
    private String adminEmail;

    public List<AdminResponse> from(List<Admin> admins){
        return admins.stream().map(admin -> new AdminResponse(admin.getAdminId(), admin.getName(), admin.getEmail()))
                .collect(Collectors.toList());
    }

    public AdminResponse from(Admin admin){
        return new AdminResponse(admin.getAdminId(), admin.getName(), admin.getEmail());
    }
}
