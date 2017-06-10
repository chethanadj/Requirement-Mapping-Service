package com.sceptra.service;

import com.sceptra.domain.developer.TechDetail;
import com.sceptra.domain.requirement.KeyWord;
import com.sceptra.finder.ApacheLibraryDesc;
import com.sceptra.model.TechnologyEntity;
import com.sceptra.model.TechnologyPackage;
import com.sceptra.repository.KeyWordRepository;
import com.sceptra.repository.TechnologyEntityRepository;
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
public class TechnologyDetailService {

    @Autowired
    KeyWordRepository keyWordRepository;
    @Autowired
    TechnologyPackageRepository packageRepository;
    @Autowired
    TechnologyEntityRepository entityRepository;
    @Autowired
    ApacheLibraryDesc apacheLibraryDesc;

    @RequestMapping(value = "techdetail", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechnologyEntity>> addBatchKeyWord(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }

    @RequestMapping(value = "techdetailfromjson", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechnologyEntity>> addBatchKeyWordFromJson(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }

    @RequestMapping(value = "addlibdata", produces = "application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechDetail>> addlibraryDetail(
            @RequestBody(required = true) TechDetail techDetail,
            BindingResult result,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        TechnologyEntity entity = new TechnologyEntity();
        entity.setTechnologyName(techDetail.getName());
        entity.setTechnologyUsages(techDetail.getUsage());
        KeyWord keyword = keyWordRepository.findByDescription(techDetail.getUsage());


        ArrayList<TechnologyEntity> data1 = entityRepository.findByTechnologyUsagesAndTechnologyName(techDetail.getUsage(), techDetail.getName());

        if (keyword != null && data1 != null && !data1.isEmpty()) {

            entityRepository.save(entity);
        }
        TechnologyPackage technologyPackage = new TechnologyPackage();
        technologyPackage.setTechnologyName(techDetail.getName());
        technologyPackage.setTechnologyPackage(techDetail.getMainPackages());

        ArrayList<TechnologyPackage> data2 = packageRepository.findByTechnologyNameAndTechnologyPackage(techDetail.getName(), techDetail.getMainPackages());
        if (data2 != null && !data2.isEmpty()) {

            packageRepository.save(technologyPackage);
        }

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }


}
