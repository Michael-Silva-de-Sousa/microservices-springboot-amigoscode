package br.com.amigoscode.web;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message
) {
}
