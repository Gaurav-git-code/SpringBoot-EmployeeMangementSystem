package com.darknightcoder.ems.model;


import com.darknightcoder.ems.enums.RolesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmsUserDto {
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8,max=20 ,message = "password must be in range of 8 to 20 character")
    private String password;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email id should be valid")
    private String email;
    @NotNull(message = "Role type cannot be blank")
    private RolesEnum roleType;
}
