package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlan;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlan2;
import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftPlan2Repository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftPlanRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
import ch.juventus.schichtenschmiede.service.entity.ShiftPlan2DTO;
import ch.juventus.schichtenschmiede.service.entity.ShiftPlanDTO;
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
//TODO Dupluzierung für die Anpassung des neuen Schichtenplans Alex und Miguel. Wir bei fertigstellung in Shiftplan übernommen.

@RestController
public class ShiftPlan2Controller {
    private final ShiftPlan2Repository shiftPlanRepository;

    private final ShiftRepository shiftRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ShiftPlan2Controller(EmployeeRepository employeeRepository, ShiftPlan2Repository shiftPlanRepository, ShiftRepository shiftRepository) {
        this.employeeRepository = employeeRepository;
        this.shiftPlanRepository = shiftPlanRepository;
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/shiftplans2")
    public List<ShiftPlan2> all() {
        return shiftPlanRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/shiftplan2/{id}")
    public ShiftPlan2 get(@PathVariable Long id) {
        return addHateoasLinks(shiftPlanRepository.getOne(id));
    }

    @PostMapping("/shiftplan2")
    public ResponseEntity<String> create(@RequestBody ShiftPlan2DTO shiftPlanDTO) {

        ShiftPlan2 persistedShiftPlan = shiftPlanRepository.save(prepareShiftPlan(new ShiftPlan2(), shiftPlanDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShiftPlan.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shiftplan2/{shiftplanId}")
    public void update(@PathVariable Long shiftplanId, @RequestBody ShiftPlan2DTO shiftPlanDTO) {
        ShiftPlan2 persistentShiftPlan = new ShiftPlan2();
        persistentShiftPlan.setStid(shiftplanId);
        shiftPlanRepository.save(prepareShiftPlan(persistentShiftPlan, shiftPlanDTO));
    }


    @PutMapping("/shiftplan2/{shiftPlanId}/addEmployee/{employeeId}")
    public void addEmployee(@PathVariable Long shiftPlanId, @PathVariable Long employeeId) {
        /*
        , @RequestBody ShiftPlanDTO shiftPlanDTO
        ShiftPlan persistentShiftPlan = new ShiftPlan();
        persistentShiftPlan.setStid(shiftPlanId);
        shiftPlanRepository.save(prepareShiftPlan(persistentShiftPlan, shiftPlanDTO));
        */
        Employee tempEmployee = employeeRepository.getOne(employeeId);

        //TODO prüfen ob Shiftplan dem Employe muss zugewissen werden
        tempEmployee.addShiftplan(shiftPlanRepository.getOne(shiftPlanId));
        employeeRepository.save(tempEmployee);
    }

    private ShiftPlan2 addHateoasLinks(ShiftPlan2 shiftPlan) {
        shiftPlan.add(linkTo(methodOn(ShiftPlan2Controller.class).get(shiftPlan.getStid())).withSelfRel());

        if (shiftPlan.getShift() != null) {
            shiftPlan.add(linkTo(methodOn(ShiftController.class).get(shiftPlan.getShift().getStid())).withRel("shift"));
        }
        if (shiftPlan.getShift().getRole() != null) {
            shiftPlan.add(linkTo(methodOn(ShiftController.class).get(shiftPlan.getShift().getRole().getStid())).withRel("role"));
        }
        return shiftPlan;
    }

    private ShiftPlan2 prepareShiftPlan(ShiftPlan2 persistentShiftPlan, ShiftPlan2DTO shiftPlanDTO){
        persistentShiftPlan.setWeekNumber(shiftPlanDTO.getWeekNumber());
        persistentShiftPlan.setYear(shiftPlanDTO.getYear());
        persistentShiftPlan.setActive(shiftPlanDTO.isActive());
        persistentShiftPlan.setShift(shiftRepository.getOne(shiftPlanDTO.getShiftId()));
        return persistentShiftPlan;
    }
}

