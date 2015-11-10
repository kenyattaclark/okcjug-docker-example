package com.box20six.docker.controller;

import com.box20six.docker.domain.RegistrationDocument;
import com.box20six.docker.model.Registration;
import com.box20six.docker.repository.RegistrationRepository;
import com.box20six.docker.repository.search.RegistrationSearchRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/registrations")
@Slf4j
public class RegistrationController {

    private RegistrationRepository repository;
    private RegistrationSearchRepository searchRepository;
    private DozerBeanMapper mapper;

    @Autowired
    public RegistrationController(RegistrationRepository repository,
                                  RegistrationSearchRepository searchRepository,
                                  DozerBeanMapper mapper) {
        this.repository = repository;
        this.searchRepository = searchRepository;
        this.mapper = mapper;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Registration create(@RequestBody Registration registration) {
        log.info("Create request: {}.", registration);

        RegistrationDocument document = mapper.map(registration, RegistrationDocument.class);
        document = repository.save(document);
        searchRepository.save(document);

        return mapper.map(document, Registration.class);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", value = "Page number of results", defaultValue = "0", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name="size", value = "Number of items to display per page", defaultValue = "20", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name="sort", value = "Sort order, e.g. ?sort=firstName or ?sort=lastName,desc", dataType = "string", paramType = "query", allowMultiple = true)
    })
    public List<Registration> list(@RequestParam(value = "q", required = false) String query, @ApiIgnore Pageable pageable) {
        Page<RegistrationDocument> page;

        if (StringUtils.isNotBlank(query)) {
            page = searchRepository.search(QueryBuilders.queryString(query), pageable);
        } else {
            page = repository.findAll(pageable);
        }

        List registrations = page.getContent().stream()
                .map(document -> mapper.map(document, Registration.class))
                .collect(Collectors.toCollection(LinkedList::new));

        log.info("List request: {}.", registrations);
        return registrations;
    }

    @RequestMapping(value = "/{id}" ,method = GET, produces = APPLICATION_JSON_VALUE)
    public Registration findOne(@PathVariable String id) {
        log.info("Registration request: {}.", id);
        RegistrationDocument document = repository.findOne(id);
        log.info("Found registration: {}.", document);
        return mapper.map(document, Registration.class);
    }
}
