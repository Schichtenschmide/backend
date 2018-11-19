package ch.juventus.example.web;

import ch.juventus.example.data.shift.Shift;
import ch.juventus.example.data.shift.ShiftRepository;
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

    private ShiftRepository shiftRepository;

    @Autowired
    public ShiftController( ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }


    @GetMapping("/shifts")
    public List<Shift> all() {
        return shiftRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/shifts/{id}")
    public Shift get(@PathVariable Long id) {
        return addHateoasLinks(shiftRepository.getOne(id));
    }

    @PostMapping("/shifts")
    public ResponseEntity<?> create(@RequestBody Shift requestShift) {

        Shift persistedShift = shiftRepository.save(requestShift);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShift.getStid()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shifts/{id}")
    public void update(@PathVariable Long id, @RequestBody Shift shift) {
        shift.setStid(id);
        shiftRepository.save(shift);
    }

    @DeleteMapping("/shifts/{id}")
    public void delete(@PathVariable Long id) {
        shiftRepository.delete(id);
    }


    public Shift addHateoasLinks(Shift shift) {
        shift.add(linkTo(methodOn(ShiftController.class).get(shift.getStid())).withSelfRel());

        if (shift.getRole() != null) {
            shift.add(linkTo(methodOn(RoleController.class).get(shift.getRole().getStid())).withRel("deparment"));
        }

        return shift;
    }


}
