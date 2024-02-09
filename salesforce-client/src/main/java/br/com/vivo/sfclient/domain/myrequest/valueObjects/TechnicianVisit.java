package br.com.vivo.sfclient.domain.myrequest.valueObjects;

import java.time.LocalDate;

public class TechnicianVisit {
    
    private final LocalDate dateOption;
    private final Period period;

    public TechnicianVisit() {
        this.dateOption = null;
        this.period = null;
    }

    public TechnicianVisit(LocalDate dateOption, Period period) {
        this.dateOption = dateOption;
        this.period = period;
    }

    public LocalDate getDateOption() {
        return dateOption;
    }

    public Period getPeriod() {
        return period;
    }

    public enum Period {
        MORNING("Manhã, entre 08h e 12h"),
        EVENING("Tarde, entre 13h e 18h");
        
        private String description;

        Period(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }        
    }
}
