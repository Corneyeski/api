package com.wefox.challenge.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class AccountDto implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    private int age;

    @NotBlank
    private String address;

    public AccountDto() {
    }

    public AccountDto(Long id, String name, String email, int age, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
