package com.catalog.backend.dto;

public class UserInsertDTO extends UserDTO {

    private static final long serialVersionUID = -2044821081244435527L;

    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
