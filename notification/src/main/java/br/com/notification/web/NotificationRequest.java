package br.com.notification.web;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message
) {
}
