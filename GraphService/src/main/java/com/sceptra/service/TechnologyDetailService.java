package com.sceptra.service;

import com.sceptra.finder.ApacheLibraryDesc;
import com.sceptra.model.TechnologyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by chiranz on 5/30/17.
 */
@RestController
public class TechnologyDetailService {

    @Autowired
    ApacheLibraryDesc apacheLibraryDesc;

    @RequestMapping(value = "techdetail" , produces = "application/json",method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechnologyEntity>> addBatchKeyWord(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }

    @RequestMapping(value = "techdetailfromjson" , produces = "application/json",method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<ArrayList<TechnologyEntity>> addBatchKeyWordFromJson(
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request) throws Exception {

        return new ResponseEntity(apacheLibraryDesc.getListData(), HttpStatus.CREATED);

    }
}
