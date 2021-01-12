package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(
            @NonNull HttpServletRequest httpServletRequest,
            @NonNull HttpServletResponse httpServletResponse,
            Object o,
            @NonNull Exception e) {
        System.out.println("Exception: ");
        System.out.println("Exception: "+e);
        if (e instanceof MaxUploadSizeExceededException) {
            System.out.println("Updating the model");
            ModelAndView modelAndView = new ModelAndView("result.html");
            String errorPrompt = "File is too large. Pick a smaller file(less than 1MB).";
            modelAndView.addObject("isSuccess", false);
            modelAndView.addObject("errorMessage", errorPrompt);
            return modelAndView;
        }
        return new ModelAndView("home.html");
    }
}
