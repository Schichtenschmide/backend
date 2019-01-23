package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.DailySchedule;
import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.repository.DailyScheduleReopistory;
import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
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
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
        List<DailySchedule> dailySchedulesOfWeek = new ArrayList<>();
        Date monday = getDateOfWeekDay(dateOfWeek, Calendar.MONDAY);
        Date sunday = getDateOfWeekDay(dateOfWeek, Calendar.SUNDAY);

        for (Iterator i = allDailySchedules.iterator(); i.hasNext(); ) {
            DailySchedule dailySchedule = (DailySchedule) i.next();
            if (dailySchedule.getDate().before(sunday) || dailySchedule.getDate().equals(sunday) || dailySchedule.getDate().after(monday) || dailySchedule.getDate().equals(monday)) {
                dailySchedulesOfWeek.add(dailySchedule);
            }
        }

        return dailySchedulesOfWeek;
    }

    public Date getDateOfWeekDay(java.util.Date dateOfWeek, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfWeek);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        return new Date(cal.getTime().getTime());
    }

    @GetMapping("/dailyschedules/{id}")
    public DailySchedule get(@PathVariable Long id) {
        return addHateoasLinks(dailyScheduleReopistory.findOne(id));
    }

    @PostMapping("/dailyschedules")
    public ResponseEntity<String> create(@RequestBody DailyScheduleDTO dailyScheduleDTO) {
        DailySchedule dailySchedule = dailyScheduleReopistory.save(createDailySchedule(dailyScheduleDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dailySchedule.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/dailyschedules/{dailySchedulesId}/addEmployee/{employeeId}")
    public void addEmployee(@PathVariable Long dailySchedulesId, @PathVariable Long employeeId) {
        Employee tempEmployee = employeeRepository.getOne(employeeId);
        DailySchedule dailySchedule = get(dailySchedulesId);
        dailySchedule.addEmployee(tempEmployee);
        dailyScheduleReopistory.save(dailySchedule);
    }

    @PutMapping("/dailyschedules/{dailyScheduleId}")
    public void update(@PathVariable Long dailyScheduleId, @RequestBody DailyScheduleDTO dailyScheduleDTO) {

        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setIdentifier(dailyScheduleId);
        dailyScheduleReopistory.save(prepareDailySchedule(dailySchedule, dailyScheduleDTO));
    }

    private DailySchedule addHateoasLinks(DailySchedule dailySchedule) {
        dailySchedule.add(linkTo(methodOn(DailyScheduleController.class).get(dailySchedule.getIdentifier())).withSelfRel());

        if (dailySchedule.getEmployees() != null && dailySchedule.getEmployees().size() > 0) {
            dailySchedule.add(linkTo(methodOn(EmployeeController.class).get(dailySchedule.getEmployees().iterator().next().getIdentifier())).withRel("employee"));
        }

        if (dailySchedule.getShift() != null) {
            dailySchedule.add(linkTo(methodOn(ShiftController.class).get(dailySchedule.getShift().getIdentifier())).withRel("shift"));
        }

        return dailySchedule;
    }

    private DailySchedule createDailySchedule(DailyScheduleDTO dailyScheduleDTO) {
        return new DailySchedule(true, new Date(dailyScheduleDTO.getDate().getTime()), shiftRepository.getOne(dailyScheduleDTO.getShiftId()));
    }

    private DailySchedule prepareDailySchedule(DailySchedule dailySchedule, DailyScheduleDTO dailyScheduleDTO) {

        dailySchedule.setActive(dailyScheduleDTO.isActive());

        dailySchedule.setDate(new Date(dailyScheduleDTO.getDate().getTime()));
        dailySchedule.addEmployee(employeeRepository.getOne(dailyScheduleDTO.getEmployeeId()));
        dailySchedule.setShift(shiftRepository.getOne(dailyScheduleDTO.getShiftId()));

        return dailySchedule;
    }
}
