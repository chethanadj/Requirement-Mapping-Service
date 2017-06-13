package com.sceptra.service;

import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.domain.technology.TechDetail;
import com.sceptra.domain.technology.TechnologyEntity;
import com.sceptra.domain.technology.TechnologyPackage;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.repository.TechnologyEntityRepository;
import com.sceptra.repository.TechnologyPackageRepository;
import com.sceptra.webfinder.ApacheLibraryDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
public class TechnologyDetailService {

    private final String base = "techdetail";
    @Autowired
    KeyWordRepository keyWordRepository;
    @Autowired
    TechnologyPackageRepository packageRepository;
    @Autowired
    TechnologyEntityRepository entityRepository;
    @Autowired
    ApacheLibraryDesc apacheLibraryDesc;

    @RequestMapping(value = base + "/add/apache",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechnologyEntity>> addBatchKeyWord(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }

    @RequestMapping(value = base + "/add",
            produces = "application/json",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<TechDetail> addLibraryDetail(
            @RequestBody(required = true) TechDetail techDetail,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        TechnologyEntity entity = new TechnologyEntity();
        entity.setTechnologyName(techDetail.getName());
        entity.setTechnologyUsages(techDetail.getUsage());
        KeyWord keyword = keyWordRepository.findByDescription(techDetail.getUsage().toString());

        ArrayList<TechnologyEntity> data1 = entityRepository
                .findByTechnologyUsagesAndTechnologyName(techDetail.getUsage(), techDetail.getName());

        if (keyword != null && data1 != null && data1.isEmpty()) {

            entityRepository.save(entity);
        } else {
            return new ResponseEntity(techDetail, HttpStatus.UNAUTHORIZED);
        }
        TechnologyPackage technologyPackage = new TechnologyPackage();
        technologyPackage.setTechnologyName(techDetail.getName());
        technologyPackage.setTechnologyPackage(techDetail.getMainPackages());

        ArrayList<TechnologyPackage> data2 = packageRepository
                .findByTechnologyNameAndTechnologyPackage(techDetail.getName(), techDetail.getMainPackages());
        if (data2 != null && data2.isEmpty()) {
            packageRepository.save(technologyPackage);
        } else {
            return new ResponseEntity(techDetail, HttpStatus.FOUND);

        }

        return new ResponseEntity(techDetail, HttpStatus.CREATED);

    }


}
