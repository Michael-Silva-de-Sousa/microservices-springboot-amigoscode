package br.com.amigoscode.web;

import lombok.Builder;

@Builder
public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
