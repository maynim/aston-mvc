package ru.maynim.astonmvc.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public String nullPointerHandler(RuntimeException runtimeException, Model model) {
        model.addAttribute("error", runtimeException);
        return "error";
    }
}
