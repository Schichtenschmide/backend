package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlan;
import ch.juventus.schichtenschmiede.service.entity.ShiftPlanDTO;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftPlanRepository;
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
    private final ShiftPlanRepository shiftPlanRepository;

    private final ShiftRepository shiftRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ShiftPlanController(EmployeeRepository employeeRepository, ShiftPlanRepository shiftPlanRepository, ShiftRepository shiftRepository) {
        this.employeeRepository = employeeRepository;
        this.shiftPlanRepository = shiftPlanRepository;
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/shiftplans")
    public List<ShiftPlan> all() {
        return shiftPlanRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/shiftplan/{id}")
    public ShiftPlan get(@PathVariable Long id) {
        return addHateoasLinks(shiftPlanRepository.getOne(id));
    }

    @PostMapping("/shiftplan")
    public ResponseEntity<String> create(@RequestBody ShiftPlanDTO shiftPlanDTO) {

        ShiftPlan persistedShiftPlan = shiftPlanRepository.save(prepareShiftPlan(new ShiftPlan(), shiftPlanDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShiftPlan.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shiftplan/{shiftplanId}")
    public void update(@PathVariable Long shiftplanId, @RequestBody ShiftPlanDTO shiftPlanDTO) {
        ShiftPlan persistentShiftPlan = new ShiftPlan();
        persistentShiftPlan.setStid(shiftplanId);
        shiftPlanRepository.save(prepareShiftPlan(persistentShiftPlan, shiftPlanDTO));
    }


    @PutMapping("/shiftplan/{shiftPlanId}/addEmployee/{employeeId}")
    public void addEmployee(@PathVariable Long shiftPlanId, @PathVariable Long employeeId) {
        /*
        , @RequestBody ShiftPlanDTO shiftPlanDTO
        ShiftPlan persistentShiftPlan = new ShiftPlan();
        persistentShiftPlan.setStid(shiftPlanId);
        shiftPlanRepository.save(prepareShiftPlan(persistentShiftPlan, shiftPlanDTO));
        */
        Employee tempEmployee = employeeRepository.getOne(employeeId);
        tempEmployee.addShiftplan(shiftPlanRepository.getOne(shiftPlanId));
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

    private ShiftPlan prepareShiftPlan(ShiftPlan persistentShiftPlan, ShiftPlanDTO shiftPlanDTO){
        persistentShiftPlan.setWeekNumber(shiftPlanDTO.getWeekNumber());
        persistentShiftPlan.setYear(shiftPlanDTO.getYear());
        persistentShiftPlan.setActive(shiftPlanDTO.isActive());
        persistentShiftPlan.setShift(shiftRepository.getOne(shiftPlanDTO.getShiftId()));
        return persistentShiftPlan;
    }
}

