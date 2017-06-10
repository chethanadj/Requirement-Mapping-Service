package com.sceptra.domain.developer;

/**
 * Created by chiranz on 6/10/17.
 */
public class TechDetail {

    String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TechDetail{" +
                "name='" + name + '\'' +
                ", usage='" + usage + '\'' +
                ", mainPackages='" + mainPackages + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getMainPackages() {
        return mainPackages;
    }

    public void setMainPackages(String mainPackages) {
        this.mainPackages = mainPackages;
    }

    String usage;
    String mainPackages;
}
