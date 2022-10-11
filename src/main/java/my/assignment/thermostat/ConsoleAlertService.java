package my.assignment.thermostat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import my.assignment.thermostat.TemperatureProcessor.TemperatureState;

/**
 * A service which outputs the alerts to the console
 */
@Service
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "threshold")
public class ConsoleAlertService implements ITemperatureAlertService{

    @Override
    public void onTemperatureChanged(TemperatureState newState){
        System.out.println(String.format("ALERT: %s", newState.getAlertMessage()));
    }
}
