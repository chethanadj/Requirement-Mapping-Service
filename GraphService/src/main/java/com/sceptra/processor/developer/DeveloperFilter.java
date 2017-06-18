package com.sceptra.processor.developer;

import com.sceptra.domain.developer.Developer;
import com.sceptra.domain.technology.Technology;
import com.sceptra.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class DeveloperFilter {

    @Autowired
    DeveloperRepository developerRepository;

    public Map<String, ArrayList<Technology>> getDeveloperList(HashSet<String> technologies) {

        ArrayList<Developer> eligibleDevelopers = new ArrayList<>();
        Map<String, ArrayList<Technology>> developersMappedWithTechnology = new HashMap<>();

        for (String technology : technologies) {
            ArrayList<Developer> developersForTechnology = developerRepository
                    .findByTechnology(technology);
            Double techMeanValue = getMeanvalueforTechnology(developersForTechnology);

            for (Developer developer : developersForTechnology) {
                if (developer.getPercentage() != null) {
                    developer.setPercentage(0.0);
                }
                Double precentage = developer.getPercentage();

                if (precentage >= techMeanValue) {
                    eligibleDevelopers.add(developer);
                }
            }

        }

        developersMappedWithTechnology = processDevelopersWithTechnology(eligibleDevelopers);
        return developersMappedWithTechnology;
    }

    public Map<String, ArrayList<Technology>> processDevelopersWithTechnology(ArrayList<Developer> allDevelopers) {
        Map<String, ArrayList<Technology>> developersMappedWithTechnology = new HashMap<>();

        for (Developer developer : allDevelopers) {

            ArrayList<Technology> technologies = null;

            if (developer.getUsername() != null) {
                technologies = developersMappedWithTechnology.get(developer.getUsername());
                if (technologies == null) {
                    technologies = new ArrayList<>();
                }

                if (developer.getTechnology() != null) {

                    if (developer.getPercentage() == null) {
                        developer.setPercentage(0.0);
                    }

                    if (developer.getOverallQuality() == null) {
                        developer.setOverallQuality(0.0);
                    }
                    Technology technology = new Technology();
                    technology.setTechnologyName(developer.getTechnology());
                    technology.setPercentage(technology.getPercentage());


                    technology.setOverallQuality(developer.getOverallQuality());
                    technologies.add(technology);


                }

                developersMappedWithTechnology.put(developer.getUsername(), technologies);
            }
        }

        return developersMappedWithTechnology;

    }

    public Double getMeanvalueforTechnology(ArrayList<Developer> allDevelopers) {

        Double developerCount = Double.valueOf(allDevelopers.size());
        Double techcount = 0.0;
        for (Developer developer : allDevelopers) {
            if (developer.getPercentage() != null) {
                developer.setPercentage(0.0);
            }
            techcount += developer.getPercentage();

        }

        if (developerCount != 0) {
            return techcount / developerCount;

        } else {
            return 0.0;
        }

    }
}
