package ch.juventus.schichtenschmiede.service.controller;

import ch.juventus.schichtenschmiede.persistency.entityNew.Shift;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.ShiftRepository2;
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
public class ShiftController2 {

    private final ShiftRepository2 shiftRepository;


    @Autowired
    public ShiftController2( ShiftRepository2 shiftRepositoryOld) {
        this.shiftRepository = shiftRepositoryOld;
    }

    @GetMapping("/shifts2")
    public List<Shift> all() {
        return shiftRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/shifts2/{id}")
    public Shift get(@PathVariable Long id) {
        return addHateoasLinks(shiftRepository.findOne(id));
    }

    @PostMapping("/shifts2")
    public ResponseEntity<String> create(@RequestBody Shift requestShift) {
        Shift persistedShift = shiftRepository.save(requestShift);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShift.getIdentifier()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shifts2s/{shiftId}")
    public void update(@PathVariable Long shiftId, @RequestBody Shift shift) {
        shift.setIdentifier(shiftId);

        shiftRepository.save(shift);
    }

    private Shift addHateoasLinks(Shift shift) {
        if (shift.getRole() != null) {
            shift.add(linkTo(methodOn(RoleController2.class).get(shift.getRole().getIdentifier())).withRel("role"));
        }
        return shift;
    }
}
