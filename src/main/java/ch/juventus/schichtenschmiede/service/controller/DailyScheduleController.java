package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.DailySchedule;
import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.DailyScheduleReopistory;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.ShiftRepository;
import ch.juventus.schichtenschmiede.service.entity.DailyScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;
    private final DailyScheduleReopistory dailyScheduleReopistory;


    @Autowired
    public DailyScheduleController(ShiftRepository shiftRepository, EmployeeRepository employeeRepository, DailyScheduleReopistory dailyScheduleReopistory) {
        this.shiftRepository = shiftRepository;
        this.employeeRepository = employeeRepository;
        this.dailyScheduleReopistory = dailyScheduleReopistory;
    }

    @GetMapping("/dailyschedules")
    public List<DailySchedule> all() {
        return allOfWeek(new java.util.Date());
    }

    @GetMapping("/dailyschedulesofweek/{dateOfWeek}")
    public List<DailySchedule> allOfWeek(@PathVariable java.util.Date dateOfWeek) {

        List<DailySchedule> allDailySchedules = dailyScheduleReopistory.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
        List<DailySchedule> dailySchedulesOfWeek = new ArrayList<DailySchedule>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfWeek);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = new Date(cal.getTime().getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date sunday = new Date(cal.getTime().getTime());

        for (Iterator i = allDailySchedules.iterator(); i.hasNext();) {
            DailySchedule ds = (DailySchedule) i.next();
            if (ds.getDate().before(sunday) || ds.getDate().equals(sunday) || ds.getDate().after(monday) || ds.getDate().equals(monday)) {
                dailySchedulesOfWeek.add(ds);
            }
        }

        return dailySchedulesOfWeek;
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


    @PutMapping("/dailyschedules/{dailySchedulesId}/addEmployee/{employeeId}")
    public void addEmployee(@PathVariable Long dailySchedulesId, @PathVariable Long employeeId) {
        /*
        , @RequestBody ShiftPlanDTO shiftPlanDTO
        ShiftPlan persistentShiftPlan = new ShiftPlan();
        persistentShiftPlan.setStid(shiftPlanId);
        shiftPlanRepository.save(prepareShifPlan(persistentShiftPlan, shiftPlanDTO));
        */
        Employee tempEmployee = employeeRepository.getOne(employeeId);
        DailySchedule dailySchedule = get(dailySchedulesId);
        dailySchedule.addEmployee(tempEmployee);
        dailyScheduleReopistory.save(dailySchedule);
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
            dailySchedule.add(linkTo(methodOn(EmployeeController.class).get(dailySchedule.getEmployees().iterator().next().getIdentifier())).withRel("employee"));
        }

        if (dailySchedule.getShift() != null) {
            dailySchedule.add(linkTo(methodOn(ShiftController.class).get(dailySchedule.getShift().getIdentifier())).withRel("shift"));
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
