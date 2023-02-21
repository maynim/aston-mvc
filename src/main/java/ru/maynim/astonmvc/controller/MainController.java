package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maynim.astonmvc.model.User;
import ru.maynim.astonmvc.repository.UserRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping()
    public String hello() {
        return "main";
    }
}
