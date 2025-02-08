package az.edu.turing.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.IntStream;

@SpringBootApplication
public class MsBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsBookingApplication.class, args);


		IntStream.range(0,100).filter(value -> value%3==0).filter(value -> value%5==0).forEach(System.out::println);
	}
}
