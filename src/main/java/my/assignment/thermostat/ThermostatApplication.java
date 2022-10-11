package my.assignment.thermostat;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An application which handles temperature inputs from the input stream
 */
@SpringBootApplication
public class ThermostatApplication implements ApplicationRunner{
	@Autowired
	TemperatureProcessor tempProcessor;

	public static void main(String[] args) {
		SpringApplication.run(ThermostatApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		try (Scanner scanner = new Scanner(System.in)){
			while(true) {
				System.out.print("Please input next themperature: ");
				String line = scanner.nextLine();
				try{
					double newTemp = Double.valueOf(line);
					tempProcessor.processTemperature(newTemp);
				} catch(NumberFormatException e) {
					System.out.println("Not a valid number");
				}
			}
		} catch (IllegalStateException | NoSuchElementException e) {
			System.out.println("System.in was closed. Exiting");
		} 
		
	}

}
