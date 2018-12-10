package ch.juventus.example.web;

import ch.juventus.example.data.role.RoleRepository;
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

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @PostMapping("role/{roleId}/shifts")
    public ResponseEntity<String> create(@PathVariable Long roleId,  @RequestBody Shift requestShift) {
        requestShift.setRole(roleRepository.getOne(roleId));
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

    private Shift addHateoasLinks(Shift shift) {
        shift.add(linkTo(methodOn(ShiftController.class).get(shift.getStid())).withSelfRel());
        if (shift.getRole() != null) {
            shift.add(linkTo(methodOn(RoleController.class).get(shift.getRole().getStid())).withRel("role"));
        }
        return shift;
    }
}
