package ch.juventus.example.web;

import ch.juventus.example.data.employee.Employee;
import ch.juventus.example.data.employee.EmployeeRepository;
import ch.juventus.example.data.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/employees")
    public List<Employee> all() {
        return employeeRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/employees/{id}")
    public Employee get(@PathVariable Long id) {
        return addHateoasLinks(employeeRepository.getOne(id));
    }

    @PostMapping("/roles/{roleId}/employee")
    public ResponseEntity<String> createEmployee(@PathVariable Long roleId,
                                                 @Valid @RequestBody Employee requestEmployee) {
        requestEmployee.setRole(roleRepository.findOne(roleId));
        Employee persistedEmployee = employeeRepository.save(requestEmployee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedEmployee.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/roles/{roleId}/employee/{employeeId}")
    public void updateEmployee(@PathVariable Long roleId, @PathVariable Long employeeId,
                               @Valid @RequestBody Employee requestEmployee) {
        requestEmployee.setStid(employeeId);
        requestEmployee.setRole(roleRepository.findOne(roleId));
        employeeRepository.save(requestEmployee);
    }

    private Employee addHateoasLinks(Employee employee) {
        employee.add(linkTo(methodOn(EmployeeController.class).get(employee.getStid())).withSelfRel());
        if (employee.getRole() != null) {
            employee.add(linkTo(methodOn(RoleController.class).get(employee.getRole().getStid())).withRel("role"));
        }
        return employee;
    }
}
