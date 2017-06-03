package com.sceptra.processor.developer;

import com.sceptra.domain.developer.Developer;
import com.sceptra.domain.developer.Technology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DeveloperFilter {

    public Map<Developer,Technology> getDeveloperList(ArrayList<Technology> technologies){
        Map<Developer,Technology> selectedDevelopers=new HashMap<>();

        ArrayList<Developer> allDevelopers=new ArrayList<>();

        technologies.forEach(technology -> {

            allDevelopers.forEach(developer -> {
//                if()
               double tempTechData=developer.getTechnologyList().get(technology.getTechnologyName());
               if(tempTechData>30.0)//thresh hold should be auto mated
               {
                   selectedDevelopers.put(developer,technology);
               }

            });

        });
        return selectedDevelopers;
    }
}
