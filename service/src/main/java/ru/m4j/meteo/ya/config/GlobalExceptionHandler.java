package ru.m4j.meteo.ya.config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);

        ModelAndView modelAndView = new ModelAndView("/error");
        String errorMessage = (throwable != null ? throwable.toString() : "Unknown error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

}
