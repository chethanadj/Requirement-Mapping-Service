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
                if (developer.getPresentage() != null) {
                    developer.setPresentage(0.0);
                }
                Double precentage = developer.getPresentage();

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

            if (developer.getName() != null) {
                technologies = developersMappedWithTechnology.get(developer.getName());
                if (technologies == null) {
                    technologies = new ArrayList<>();
                }

                if (developer.getTechnology() != null) {
                    if (developer.getPresentage() != null) {
                        developer.setPresentage(0.0);
                        Technology technology = new Technology();
                        technology.setTechnologyName(developer.getTechnology());
                        technology.setPresentage(technology.getPresentage());

                        if (developer.getOverollQuality() == null) {
                            developer.setOverollQuality(0.0);
                        }
                        technology.setOverollQuality(developer.getOverollQuality());
                        technologies.add(technology);

                    }

                }

                developersMappedWithTechnology.put(developer.getName(), technologies);
            }
        }

        return developersMappedWithTechnology;

    }

    public Double getMeanvalueforTechnology(ArrayList<Developer> allDevelopers) {

        Double developerCount = Double.valueOf(allDevelopers.size());
        Double techcount = 0.0;
        for (Developer developer : allDevelopers) {
            if (developer.getPresentage() != null) {
                developer.setPresentage(0.0);
            }
            techcount += developer.getPresentage();

        }

        if (developerCount != 0) {
            return techcount / developerCount;

        } else {
            return 0.0;
        }

    }
}
