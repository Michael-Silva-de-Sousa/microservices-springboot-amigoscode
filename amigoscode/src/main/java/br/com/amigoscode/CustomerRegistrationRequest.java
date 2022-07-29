package br.com.amigoscode;

public record CustomerRegistrationRequest(
        String firstName,
        String lstName,
        String email) {
}
