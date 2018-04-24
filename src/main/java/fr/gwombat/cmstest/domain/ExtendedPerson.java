package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.mapping.annotations.CmsTemporal;

import java.time.LocalDate;

public class ExtendedPerson extends Person {

    private String    customProperty;
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCustomProperty() {
        return customProperty;
    }

    public void setCustomProperty(String customProperty) {
        this.customProperty = customProperty;
    }

    @CmsTemporal(format = "dd/MM/yyyy")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "ExtendedPerson{" +
                "birthDate=" + birthDate +
                "} " + super.toString();
    }
}
