package it.amrito.pastryshopbe.utils;

import it.amrito.pastryshopbe.config.ApplicationConfig;

import java.time.LocalDate;

public class UtilMethods {

    private UtilMethods(){
        //unused
    }


    public static Double calculatePrice(Double basicPrice, LocalDate productionDate, ApplicationConfig applicationConfig){
        LocalDate today = LocalDate.now();
        double percentage;
        if(today.equals(productionDate) || today.minusDays(1L).equals(productionDate)){
            percentage = applicationConfig.getFirstDayPercentage();
        }
        else if (today.minusDays(2L).equals(productionDate)) {
            percentage = applicationConfig.getSecondDayPercentage();
        }
        else if (today.minusDays(3L).equals(productionDate)) {
            percentage = applicationConfig.getThirdDayPercentage();
        }
        else {
            return 0d;
        }

        return (basicPrice * percentage) / 100;
    }
}
