package ch.juventus.schichtenschmiede.service.controller;


import ch.juventus.schichtenschmiede.persistency.entityNew.Role;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.RoleRepository2;
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
public class RoleController2 {
    private RoleRepository2 roleRepository2;

    @Autowired
    public RoleController2(RoleRepository2 roleRepository) {
        this.roleRepository2 = roleRepository;
    }

    @GetMapping("/roles2")
    public List<Role> all() {
        return roleRepository2.findAll().stream()
                .map(e->addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/roles2")
    public ResponseEntity<String> create(@RequestBody Role role) {

        Role persistedRole = roleRepository2.save(role);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedRole.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/roles2/{id}")
    public Role get(@PathVariable Long id) {
        return addHateoasLinks(roleRepository2.findOne(id));
    }

    @PutMapping("/roles2/{id}")
    public void update(@PathVariable Long id, @RequestBody Role role) {
        role.setIdentifier(id);

        roleRepository2.save(role);
    }

    private Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController2.class).get(role.getIdentifier())).withSelfRel());
        return role;
    }
}
