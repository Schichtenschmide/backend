package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.service.entity.EmployeeDTO;
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
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/employees")
    public List<Employee> all() {
        return employeeRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/employees/{id}")
    public Employee get(@PathVariable Long id) {
        return addHateoasLinks(employeeRepository.getOne(id));
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee persistentEmployee = employeeRepository.save(prepareEmployee(new Employee(), employeeDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistentEmployee.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@PathVariable Long employeeId,
                               @RequestBody EmployeeDTO employeeDTO) {
        Employee persistentEmployee = new Employee();
        persistentEmployee.setIdentifier(employeeId);
        employeeRepository.save(prepareEmployee(persistentEmployee, employeeDTO));
    }

    private Employee addHateoasLinks(Employee employee) {
        employee.add(linkTo(methodOn(EmployeeController.class).get(employee.getIdentifier())).withSelfRel());
        if (employee.getRole() != null) {
            employee.add(linkTo(methodOn(RoleController.class).get(employee.getRole().getIdentifier())).withRel("role"));
        }
        return employee;
    }

    private Employee prepareEmployee(Employee persistentEmployee, EmployeeDTO employeeDTO) {
        persistentEmployee.setFirstName(employeeDTO.getFirstName());
        persistentEmployee.setLastName(employeeDTO.getLastName());
        persistentEmployee.setActive(employeeDTO.isActive());
        persistentEmployee.setEmploymentRate(employeeDTO.getEmploymentRate());
        persistentEmployee.setRole(roleRepository.findOne(employeeDTO.getRoleId()));
        return persistentEmployee;
    }

}
