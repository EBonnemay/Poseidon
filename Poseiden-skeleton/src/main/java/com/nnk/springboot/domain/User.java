package com.nnk.springboot.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;

/**
 * This entity class defines User objects. It has two constructors : one without parameter,
 * one with parameters 'username', 'password' , 'fullname', 'role'
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NonNull
    @NotEmpty(message = "Username is mandatory")
    private String username;
    @NonNull
    @NotEmpty(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "password must contain at least 1 uppercase, 1 digit, 1 special character and 8 characters")
    private String password;
    @NonNull
    @NotEmpty(message = "FullName is mandatory")
    private String fullname;
    @NonNull
    @NotEmpty(message = "Role is mandatory")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
