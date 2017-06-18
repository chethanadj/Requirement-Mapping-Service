package com.sceptra.service;

import com.sceptra.domain.developer.Developer;
import com.sceptra.domain.technology.TechnologyPackage;
import com.sceptra.repository.DeveloperRepository;
import com.sceptra.repository.TechnologyPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class DeveloperService {
    private final String base = "developer";
    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    TechnologyPackageRepository technologyPackageRepository;

    @RequestMapping(value = base + "/add/one",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Developer> addDeveloper(
            @RequestBody(required = true) Developer developer,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        if (developer.getUsername() == null || developer.getTechnology() == null) {
            return new ResponseEntity<Developer>(developer, HttpStatus.BAD_REQUEST);
        }

        TechnologyPackage technologyPackage=technologyPackageRepository
                .findByAndTechnologyPackage(developer.getTechnology());

        if (technologyPackage==null){
            return new ResponseEntity<Developer>(developer, HttpStatus.BAD_REQUEST);

        }

        ArrayList<Developer> developers = developerRepository
                .findByUsernameAndTechnology(developer.getUsername(), developer.getTechnology());

        if (developers.size() > 0) {
            return new ResponseEntity(developers, HttpStatus.UNAUTHORIZED);

        } else {
            Developer developer1 = developerRepository.save(developer);
            return new ResponseEntity(developer1, HttpStatus.CREATED);

        }

    }


}
