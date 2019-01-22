package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entity.Shift;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
import ch.juventus.schichtenschmiede.service.entity.ShiftDTO;
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
    public ShiftController(ShiftRepository shiftRepository, RoleRepository roleRepository) {
        this.shiftRepository = shiftRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/shifts")
    public List<Shift> all() {
        return shiftRepository.findAll().stream()
                .map(this::addHateoasLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/shifts/{id}")
    public Shift get(@PathVariable Long id) {
        return addHateoasLinks(shiftRepository.findOne(id));
    }

    @PostMapping("/shifts")
    public ResponseEntity<String> create(@RequestBody ShiftDTO shiftDTO) {
        Shift persistedShift = shiftRepository.save(prepareShift(new Shift(), shiftDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShift.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shifts/{shiftId}")
    public void update(@PathVariable Long shiftId, @RequestBody ShiftDTO shiftDTO) {

        Shift persistedShift = new Shift();
        persistedShift.setIdentifier(shiftId);
        shiftRepository.save(prepareShift(persistedShift, shiftDTO));
    }

    private Shift addHateoasLinks(Shift shift) {
        shift.add(linkTo(methodOn(ShiftController.class).get(shift.getIdentifier())).withSelfRel());
        if (shift.getRole() != null) {
            shift.add(linkTo(methodOn(RoleController.class).get(shift.getRole().getIdentifier())).withRel("role"));
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
        persistentShift.setEmployeeCount(shiftDTO.getEmployeeCount());
        persistentShift.setActive(shiftDTO.isActive());
        persistentShift.setRole(roleRepository.getOne(shiftDTO.getRoleId()));
        return persistentShift;
    }
}
