package my.assignment.thermostat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A Service handling the new temperature changes
 */
@Service
public class TemperatureProcessor {
    @Value("${threshold.freezing}")
    public double FREEZING_THRESHOLD;
    @Value("${threshold.boiling}")
    public double BOILING_THRESHOLD;
    @Value("${threshold.fluctation}")
    public double FLUCTUATION_THRESHOLD; 

    private TemperatureState temperatureState;

    @Autowired
    ITemperatureAlertService alert;

    @Autowired
    public TemperatureProcessor(ConsoleAlertService freezingAlert) {
        this.temperatureState = TemperatureState.UNFREEZING;
    }
    public void processTemperature(double newTemp){
        TemperatureState newState = nextState(newTemp);
        if(newState != temperatureState) {
            alert.onTemperatureChanged(newState);
        }
        temperatureState = newState;
    }

    /**
     * calculates the next temperature state according to the current and new temperature
     */
    private TemperatureState nextState(double newTemp){
        switch(temperatureState){
            case FREEZING:
                if(newTemp > FREEZING_THRESHOLD + FLUCTUATION_THRESHOLD){
                    return TemperatureState.UNFREEZING;
                }
                break;
            case BOILING: 
                if(newTemp < BOILING_THRESHOLD - FLUCTUATION_THRESHOLD){
                    return TemperatureState.UNBOILING;
                }
                break;
            case UNFREEZING:
            case UNBOILING:
                if(newTemp >= BOILING_THRESHOLD){
                    return TemperatureState.BOILING;
                } else if(newTemp <= FREEZING_THRESHOLD) {
                    return TemperatureState.FREEZING;
                } 
            }
            return this.temperatureState;
    }
    
    /**
     * The temperature state
     */
    public enum TemperatureState{
        FREEZING("Freezing!"),
        UNFREEZING("Unfreezing!"),
        BOILING("Boiling!"),
        UNBOILING("Unboiling!");

        private String alertMessage;

        private TemperatureState(String alertMessage){
            this.alertMessage = alertMessage;
        }
        public String getAlertMessage(){
            return alertMessage;
        }
    }
}

