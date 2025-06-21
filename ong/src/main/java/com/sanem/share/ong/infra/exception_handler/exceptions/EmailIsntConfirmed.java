package com.sanem.share.ong.infra.exception_handler.exceptions;

public class EmailIsntConfirmed extends RuntimeException{
    public EmailIsntConfirmed (String message) {
        super(message);
    }
}
