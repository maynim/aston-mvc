package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.service.RoleService;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping()
    public String findAllRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "roles/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        return "/roles/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        return "roles/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("role") Role role) {
        roleService.update(id, role);
        return "redirect:/roles";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        roleService.deleteById(id);
        return "redirect:/roles";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("role") Role role) {
        return "roles/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }
}
