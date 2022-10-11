package my.assignment.thermostat;

import my.assignment.thermostat.TemperatureProcessor.TemperatureState;

/**
 * Service which handles the temperature alerts
 */
public interface ITemperatureAlertService {

    public void onTemperatureChanged(TemperatureState newState);

    

}