package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.persistency.entity.Shift;
import ch.juventus.schichtenschmiede.service.entity.ShiftDTO;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
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
public class ShiftController {

    private final ShiftRepository shiftRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public ShiftController(RoleRepository roleRepository, ShiftRepository shiftRepository) {
        this.roleRepository = roleRepository;
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/shifts")
    public List<Shift> all() {
        return shiftRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/shift/{id}")
    public Shift get(@PathVariable Long id) {
        return addHateoasLinks(shiftRepository.getOne(id));
    }

    @PostMapping("/shift")
    public ResponseEntity<String> create(@RequestBody ShiftDTO requestShift) {
        Shift persistedShift = shiftRepository.save(prepareShift(new Shift(), requestShift));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShift.getStid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shift/{shiftId}")
    public void update(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {
        Shift persistedShift = new Shift();
        persistedShift.setStid(shiftId);
        shiftRepository.save(prepareShift(persistedShift, shiftDTO));
    }

    private Shift addHateoasLinks(Shift shift) {
        shift.add(linkTo(methodOn(ShiftController.class).get(shift.getStid())).withSelfRel());
        if (shift.getRole() != null) {
            shift.add(linkTo(methodOn(RoleController.class).get(shift.getRole().getStid())).withRel("role"));
        }
        return shift;
    }

    private Shift prepareShift(Shift persistentShift, ShiftDTO shiftDTO ){
        persistentShift.setName(shiftDTO.getName());
        persistentShift.setStartTime(shiftDTO.getStartTime());
        persistentShift.setEndTime(shiftDTO.getEndTime());
        persistentShift.setMonday(shiftDTO.isMonday());
        persistentShift.setTuesday(shiftDTO.isTuesday());
        persistentShift.setWednesday(shiftDTO.isWednesday());
        persistentShift.setThursday(shiftDTO.isThursday());
        persistentShift.setFriday(shiftDTO.isFriday());
        persistentShift.setSaturday(shiftDTO.isSaturday());
        persistentShift.setSunday(shiftDTO.isSunday());
        persistentShift.setShorthand(shiftDTO.getShorthand());
        persistentShift.setEmployeeCount(shiftDTO.getEmployeeCount());
        persistentShift.setActive(shiftDTO.isActive());
        persistentShift.setRole(roleRepository.getOne(shiftDTO.getRoleId()));
        return persistentShift;
    }
}
