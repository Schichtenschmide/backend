package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entityNew.DailySchedule;
import ch.juventus.schichtenschmiede.persistency.entityNew.Shift;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.DailyScheduleReopistory;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.EmployeeRepository2;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.ShiftRepository2;
import ch.juventus.schichtenschmiede.service.entity.DailyScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author : ${user}
 * @since: ${date}
 */
@RestController
public class DailyScheduleController {

    private final ShiftRepository2 shiftRepository;
    private final EmployeeRepository2 employeeRepository;
    private final DailyScheduleReopistory dailyScheduleReopistory;


    @Autowired
    public DailyScheduleController(ShiftRepository2 shiftRepository, EmployeeRepository2 employeeRepository, DailyScheduleReopistory dailyScheduleReopistory) {
        this.shiftRepository = shiftRepository;
        this.employeeRepository = employeeRepository;
        this.dailyScheduleReopistory = dailyScheduleReopistory;
    }

    @GetMapping("/dailyschedules")
    public List<DailySchedule> all() {
        return dailyScheduleReopistory.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }


    @GetMapping("/dailyschedules/{id}")
    public DailySchedule get(@PathVariable Long id) {
        return addHateoasLinks(dailyScheduleReopistory.findOne(id));
    }

    @PostMapping("/dailyschedules")
    public ResponseEntity<String> create(@RequestBody DailyScheduleDTO dailyScheduleDTO) {
        DailySchedule dailySchedule = dailyScheduleReopistory.save(prepareDailySchedule(new DailySchedule(), dailyScheduleDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dailySchedule.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/dailyschedules/{dailyschedule_id}")
    public void update(@PathVariable Long dailyschedule_id, @RequestBody DailyScheduleDTO dailyScheduleDTO) {

        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setIdentifier(dailyschedule_id);
        dailyScheduleReopistory.save(prepareDailySchedule(dailySchedule, dailyScheduleDTO));
    }

    private DailySchedule addHateoasLinks(DailySchedule dailySchedule) {
        dailySchedule.add(linkTo(methodOn(DailyScheduleController.class).get(dailySchedule.getIdentifier())).withSelfRel());

        if (dailySchedule.getEmployees() != null) {
            dailySchedule.add(linkTo(methodOn(EmployeeController2.class).get(dailySchedule.getEmployees().iterator().next().getIdentifier())).withRel("employee"));
        }

        if (dailySchedule.getShift() != null) {
            dailySchedule.add(linkTo(methodOn(ShiftController2.class).get(dailySchedule.getShift().getIdentifier())).withRel("shift"));
        }

        return dailySchedule;
    }
    private DailySchedule prepareDailySchedule(DailySchedule dailySchedule, DailyScheduleDTO dailyScheduleDTO ){


        dailySchedule.setActive(dailyScheduleDTO.isActive());

        dailySchedule.setDate(new Date(dailyScheduleDTO.getDate().getTime()));
        dailySchedule.addEmployee(employeeRepository.getOne(dailyScheduleDTO.getEmployeeId()));
        dailySchedule.setShift(shiftRepository.getOne(dailyScheduleDTO.getShiftId()));

        return dailySchedule;
    }
}
