package ch.juventus.example.web;

import ch.juventus.example.data.role.Role;
import ch.juventus.example.data.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
                .map(d -> addHateoasLinks(d))
                .collect(Collectors.toList());
    }

    @GetMapping("/roles/{id}")
    public Role get(@PathVariable Long id) {
        return addHateoasLinks(roleRepository.findOne(id));
    }

    public Role addHateoasLinks(Role role) {
        role.add(linkTo(methodOn(RoleController.class).get(role.getStid())).withSelfRel());
        role.getEmployees().forEach(
                e -> role.add(linkTo(methodOn(EmployeeController.class).get(e.getStid())).withRel("employees"))
        );
        return role;
    }
}
