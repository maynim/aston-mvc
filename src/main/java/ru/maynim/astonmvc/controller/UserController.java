package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.model.Role;
import ru.maynim.astonmvc.model.User;
import ru.maynim.astonmvc.repository.RoleRepository;
import ru.maynim.astonmvc.repository.UserRepository;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findByIdWithRoles(id).orElseThrow(() -> {
            throw new RuntimeException("Empty user");
        }));
        return "/users/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty user");
        }));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userRepository.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/set-roles")
    public String setRoles(@PathVariable("id") long id, @ModelAttribute("role") Role role, Model model) {
        model.addAttribute("rolesToSave", roleRepository.findAll());
        model.addAttribute("id", id);
        return "users/set-roles";
    }

    @PostMapping("/{id}/set-roles")
    public String setRoles(@PathVariable("id") long id, @ModelAttribute("user") Role role) {
        userRepository.addRole(id, role);
        return "redirect:/users/{id}";
    }
}
