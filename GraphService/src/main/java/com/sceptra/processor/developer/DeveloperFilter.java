package com.sceptra.processor.developer;

import com.sceptra.domain.developer.Developer;
import com.sceptra.domain.developer.Technology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chiranz on 5/27/17.
 */
public class DeveloperFilter {

    public Map<Developer,Technology> getDeveloperList(ArrayList<Technology> technologies){

        Map<Developer,Technology> selectedDevelopers=new HashMap<>();
        ArrayList<Developer> allDevelopers=new ArrayList<>();

        technologies.forEach(technology -> {

            allDevelopers.forEach(developer -> {
//                if()
               double tempTechData=developer.getTechnologyList().get(technology.getTechnologyName());
               if(tempTechData>getMeanvalueforTechnology(technology.getTechnologyName()))//thresh hold should be auto mated
               {  //add mean
                   selectedDevelopers.put(developer,technology);
               }

            });

        });
        return selectedDevelopers;
    }


    public Double getMeanvalueforTechnology(String technology){

        ArrayList<Developer> allDevelopers=new ArrayList<>();
        final Double[] developers = {0.0};
        final Double[] techcount = {0.0};
        allDevelopers.forEach(developer ->{
            if(developer.getTechnologyList().containsKey(technology)) {
                developers[0] +=1.0;
                Double techofDev = developer.getTechnologyList().get(technology);
                techcount[0] +=techofDev;
            }

        });

        return techcount[0]/developers[0];
    }
}
