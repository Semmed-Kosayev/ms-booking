package az.edu.turing.booking.exception;

public class InvalidFlightDateException extends RuntimeException {
    public InvalidFlightDateException(String message) {
        super(message);
    }
}
