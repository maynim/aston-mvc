package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.service.RoleService;
import ru.maynim.astonmvc.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findByIdWithRoles(id));
        return "/users/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/set-roles")
    public String setRoles(@PathVariable("id") long id, @ModelAttribute("role") Role role, Model model) {
        model.addAttribute("rolesToSave", roleService.findAll());
        model.addAttribute("id", id);
        return "users/set-roles";
    }

    @PostMapping("/{id}/set-roles")
    public String setRoles(@PathVariable("id") long id, @ModelAttribute("user") Role role) {
        userService.addRole(id, role);
        return "redirect:/users/{id}";
    }
}
