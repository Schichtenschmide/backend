package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entityNew.Employee;
import ch.juventus.schichtenschmiede.persistency.entityNew.Role;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.EmployeeRepository2;
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
public class EmployeeController2 {

    private final EmployeeRepository2 employeeRepository;


    @Autowired
    public EmployeeController2(EmployeeRepository2 employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees2")
    public List<Employee> all() {
        return employeeRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/employees2/{id}")
    public Employee get(@PathVariable Long id) {
        return addHateoasLinks(employeeRepository.getOne(id));
    }

    @PostMapping("/employees2")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        Employee persistentEmployee = employeeRepository.save(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistentEmployee.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/employees2/{employeeId}")
    public void updateEmployee(@PathVariable Long employeeId,
                               @RequestBody Employee employee) {
        employee.setIdentifier(employeeId);
        employeeRepository.save(employee);
    }

    private Employee addHateoasLinks(Employee employee) {
        employee.add(linkTo(methodOn(EmployeeController2.class).get(employee.getIdentifier())).withSelfRel());
        if (employee.getRole() != null) {
            employee.add(linkTo(methodOn(RoleController2.class).get(employee.getRole().getIdentifier())).withRel("role"));
        }
        return employee;
    }

}
