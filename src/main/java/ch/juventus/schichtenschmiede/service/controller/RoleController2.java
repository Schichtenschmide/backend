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
    private RoleRepository2 roleRepository;

    @Autowired
    public RoleController2(RoleRepository2 roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles2")
    public List<Role> all() {
        List<Role> list = roleRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());

        return list;
    }

    @PostMapping("/roles2")
    public ResponseEntity<String> create(@RequestBody Role role) {

        Role persistedRole = roleRepository.save(role);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedRole.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/role2/{id}")
    public Role get(@PathVariable Long id) {
        return addHateoasLinks(roleRepository.findOne(id));
    }

    @PutMapping("/role2/{id}")
    public void update(@PathVariable Long id, @RequestBody Role role) {
        role.setIdentifier(id);

        roleRepository.save(role);
    }

    private Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController2.class).get(role.getIdentifier())).withSelfRel());
        return role;
    }
}
