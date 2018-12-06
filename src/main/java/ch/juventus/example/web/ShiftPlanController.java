package ch.juventus.example.web;

import ch.juventus.example.data.shiftplan.ShiftPlan;
import ch.juventus.example.data.shiftplan.ShiftPlanRepository;
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

    private ShiftPlanRepository shiftPlanRepository;

    @Autowired
    public ShiftPlanController(ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
    }

    @GetMapping("/shiftplans")
    public List<ShiftPlan> all() {
        return shiftPlanRepository.findAll().stream()
                .map(e -> addHateoasLinks(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/shiftplanners/{id}")
    public ShiftPlan get(@PathVariable Long id) {
        return addHateoasLinks(shiftPlanRepository.getOne(id));
    }

    @PostMapping("/shiftplans")
    public ResponseEntity<String> create(@RequestBody ShiftPlan requestShift) {

        ShiftPlan persistedShiftPlan = shiftPlanRepository.save(requestShift);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedShiftPlan.getStid()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shiftplans/{id}")
    public void update(@PathVariable Long id, @RequestBody ShiftPlan shiftPlan) {
        shiftPlan.setStid(id);
        shiftPlanRepository.save(shiftPlan);
    }

    /*
    @DeleteMapping("/shiftplans/{id}")
    public void delete(@PathVariable Long id) {
        shiftPlanRepository.delete(id);
    }
    */

    public ShiftPlan addHateoasLinks(ShiftPlan shiftPlan) {
        shiftPlan.add(linkTo(methodOn(ShiftPlanController.class).get(shiftPlan.getStid())).withSelfRel());

        if (shiftPlan.getShift() != null) {
            shiftPlan.add(linkTo(methodOn(ShiftController.class).get(shiftPlan.getShift().getStid())).withRel("shift"));
        }
        return shiftPlan;
    }
}

