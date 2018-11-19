package ch.juventus.example.web;

import ch.juventus.example.data.employee.Employee;
import ch.juventus.example.data.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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

    @PostMapping(path = "/employees", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity<String> create(@RequestBody Employee requestEmployee, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

        }
            Employee persistedEmployee = employeeRepository.save(requestEmployee);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(persistedEmployee.getStid()).toUri();

            return ResponseEntity.created(location).build();

    }

    @PutMapping("/employees/{id}")
    public void update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setStid(id);
        employeeRepository.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable Long id) {
        employeeRepository.delete(id);
    }


    public Employee addHateoasLinks(Employee employee) {
        employee.add(linkTo(methodOn(EmployeeController.class).get(employee.getStid())).withSelfRel());
        if (employee.getRole() != null) {
            employee.add(linkTo(methodOn(RoleController.class).get(employee.getRole().getStid())).withRel("role"));
        }
        return employee;
    }
}
