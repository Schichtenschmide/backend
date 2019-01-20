package ch.juventus.schichtenschmiede.service.controller;


import ch.juventus.schichtenschmiede.persistency.entity.Role;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.RoleRepository;
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
                .map(e->addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/roles")
    public ResponseEntity<String> create(@RequestBody Role role) {

        Role persistedRole = roleRepository.save(role);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedRole.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/roles/{id}")
    public Role get(@PathVariable Long id) {
        return addHateoasLinks(roleRepository.findOne(id));
    }

    @PutMapping("/roles/{id}")
    public void update(@PathVariable Long id, @RequestBody Role role) {
        role.setIdentifier(id);

        roleRepository.save(role);
    }

    private Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController.class).get(role.getIdentifier())).withSelfRel());
        return role;
    }
}