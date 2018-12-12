package ch.juventus.example.web;

import ch.juventus.example.data.employee.Employee;
import ch.juventus.example.data.employee.EmployeeRepository;
import ch.juventus.example.data.role.RoleRepository;
import ch.juventus.example.data.shift.ShiftRepository;
import ch.juventus.example.data.shiftplan.ShiftPlan;
import ch.juventus.example.data.shiftplan.ShiftPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author : ${user}
 * @since: ${date}
 */

@RestController
public class ShiftPlanController {
    @Autowired
    private ShiftPlanRepository shiftPlanRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/shiftplans")
    public List<ShiftPlan> all() {
        return shiftPlanRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/shiftplanners/{id}")
    public ShiftPlan get(@PathVariable Long id) {
        return addHateoasLinks(shiftPlanRepository.getOne(id));
    }

    @PostMapping("/shift/{shiftId}/shiftplans")
    public ResponseEntity<String> create(@PathVariable Long shiftId,@RequestBody ShiftPlan requestShiftPlan) {

        requestShiftPlan.setShift(shiftRepository.getOne(shiftId));
        ShiftPlan persistedShiftPlan = shiftPlanRepository.save(requestShiftPlan);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShiftPlan.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shiftplans/{id}")
    public void update(@PathVariable Long id, @RequestBody ShiftPlan shiftPlan) {
        shiftPlan.setStid(id);
        shiftPlanRepository.save(shiftPlan);
    }

    @PutMapping("/shiftplan/{shiftplanId}/employee/{employeeId}")
    public void addEmployee(@PathVariable Long shiftplanId, @PathVariable Long employeeId) {
        Employee tempEmployee = employeeRepository.getOne(employeeId);
        tempEmployee.addShiftplan(shiftPlanRepository.getOne(shiftplanId));
        employeeRepository.save(tempEmployee);
    }

    private ShiftPlan addHateoasLinks(ShiftPlan shiftPlan) {
        shiftPlan.add(linkTo(methodOn(ShiftPlanController.class).get(shiftPlan.getStid())).withSelfRel());

        if (shiftPlan.getShift() != null) {
            shiftPlan.add(linkTo(methodOn(ShiftController.class).get(shiftPlan.getShift().getStid())).withRel("shift"));
        }
        if (shiftPlan.getShift().getRole() != null) {
            shiftPlan.add(linkTo(methodOn(ShiftController.class).get(shiftPlan.getShift().getRole().getStid())).withRel("role"));
        }
        return shiftPlan;
    }
}

