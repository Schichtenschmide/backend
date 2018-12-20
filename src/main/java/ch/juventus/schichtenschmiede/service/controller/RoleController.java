package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.Role;
import ch.juventus.schichtenschmiede.service.entity.RoleDTO;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    public List<Role> all() {
        return roleRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @PostMapping("/roles")
    public ResponseEntity<String> create(@RequestBody RoleDTO role) {
        Role persistentRole = new Role();

        persistentRole.setStid(role.getStid());
        persistentRole.setName(role.getName());
        persistentRole.setActive(role.isActive());

        Role persistedRole = roleRepository.save(persistentRole);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedRole.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/role/{id}")
    public Role get(@PathVariable Long id) {
        return addHateoasLinks(roleRepository.findOne(id));
    }

    @PutMapping("/role/{id}")
    public void update(@PathVariable Long id, @RequestBody RoleDTO role) {
        Role persistentRole = new Role();

        persistentRole.setStid(id);
        persistentRole.setName(role.getName());
        persistentRole.setActive(role.isActive());

        roleRepository.save(persistentRole);
    }

    private Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController.class).get(role.getStid())).withSelfRel());
        return role;
    }
}
