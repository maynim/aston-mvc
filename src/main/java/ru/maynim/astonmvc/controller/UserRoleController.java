package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maynim.astonmvc.repository.RoleRepository;
import ru.maynim.astonmvc.repository.UserRepository;
import ru.maynim.astonmvc.repository.UserRoleRepository;

@Controller
@RequestMapping("/user-roles")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAllWithRoles());
        return "user-roles/findAll";
    }
    @DeleteMapping("/{user_id}-{role_id}")
    public String delete(@PathVariable("user_id") long userId, @PathVariable("role_id") long roleId) {
        userRoleRepository.delete(userId, roleId);
        return "redirect:/user-roles";
    }
}
