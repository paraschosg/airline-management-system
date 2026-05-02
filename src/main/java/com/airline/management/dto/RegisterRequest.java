package com.airline.management.dto;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String fullname;
    private String at;
    private String role;

    private String afm;
    private String address;
    private String employeeCode;

    public RegisterRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getAt() { return at; }
    public void setAt(String at) { this.at = at; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
}