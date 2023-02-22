package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.repository.RoleRepository;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "roles/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("role", roleRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty role");
        }));
        return "/roles/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("role", roleRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty role");
        }));
        return "roles/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("role") Role role) {
        roleRepository.update(id, role);
        return "redirect:/roles";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        roleRepository.deleteById(id);
        return "redirect:/roles";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("role") Role role) {
        return "roles/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("role") Role role) {
        roleRepository.save(role);
        return "redirect:/roles";
    }
}
