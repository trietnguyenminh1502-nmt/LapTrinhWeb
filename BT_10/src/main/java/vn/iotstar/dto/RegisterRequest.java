package vn.iotstar.dto;

public record RegisterRequest(
    String email, 
    String password, 
    String fullName
) {}