package ch.juventus.schichtenschmiede.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ${user}
 * @since: ${date}
 */

@RestController
public class HomepageController {

    @GetMapping("/")
    public String showHome() {
        return "Please go to <a href='/swagger-ui.html'>swagger</a> to see what the API offers";
    }
}
