package ru.maynim.astonmvc.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.repository.RoleRepository;
import ru.maynim.astonmvc.service.RoleService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllWithUsers() {
        List<Role> foundRoles = roleRepository.findAllWithUsers();
        log.debug("Found role list: {}", foundRoles);
        return foundRoles;
    }

    @Override
    public Role findById(long id) {
        Role foundRole = roleRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Role with ID: " + id + " was not found");
        });

        log.debug("Found role: {}", foundRole);
        return foundRole;
    }

    @Override
    public void update(long id, Role role) {
        int updatedRows = roleRepository.update(id, role);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("Role with ID: " + id + " was not found");
        } else {
            log.debug("Role with ID: {} was updated to Role: {}", id, role);
        }
    }

    @Override
    public void deleteById(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        List<Role> foundRoleList = roleRepository.findAll();

        log.debug("Found roles: {}", foundRoleList);

        return foundRoleList;
    }
}
