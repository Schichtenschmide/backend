package ch.juventus.schichtenschmiede.service.controller;


import ch.juventus.schichtenschmiede.persistency.entity.Role;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.service.entity.RoleDTO;
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
    public ResponseEntity<String> create(@RequestBody RoleDTO roleDTO) {

        Role persistedRole = roleRepository.save(prepareRole(new Role(), roleDTO));

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
    public void update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Role persistenRole = new Role();
        persistenRole.setIdentifier(id);

        roleRepository.save(prepareRole(persistenRole, roleDTO));
    }

    private Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController.class).get(role.getIdentifier())).withSelfRel());
        return role;
    }
    private Role prepareRole(Role persistentRole, RoleDTO roleDTO){
        persistentRole.setName(roleDTO.getName());
        persistentRole.setActive(roleDTO.isActive());
        return persistentRole;
    }
}
