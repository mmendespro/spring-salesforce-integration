package br.com.vivo.sfclient.application.dto;

public record CaseResponse(String id, String number, String subject, String reason, String status, Boolean omniChannel, String account) {
}
