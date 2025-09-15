package com.iadkouskaya.crm.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle", "Nieprawidłowe żądanie");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/custom";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Nie znaleziono");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/custom";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Strona nie została znaleziona");
        model.addAttribute("errorMessage", "Nie znaleziono żądanej strony.");
        return "error/custom";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Wystąpił błąd");
        model.addAttribute("errorMessage", "Coś poszło nie tak. Spróbuj ponownie później.");
        return "error/custom";
    }
}
