package com.box20six.docker.controllers;

import com.box20six.docker.models.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/registrations")
@Slf4j
public class RegistrationController {

    private List<Registration> registrations = new ArrayList<>();

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Registration create(@RequestBody Registration registration) {
        log.info("Create request: {}.", registration);
        registrations.add(registration);
        return registration;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Registration> list() {
        log.info("List request: {}.", registrations);
        return registrations;
    }
}
