package pl.sdacademy.backend.Errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sdacademy.backend.reservation.NoSuchReservationException;
import pl.sdacademy.backend.room.NoSuchRoomException;
import pl.sdacademy.backend.user.NoSuchUserException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Set<String>> handle(MethodArgumentNotValidException e) {
        LOGGER.info(e.getMessage());
        return e.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handle(ArrayIndexOutOfBoundsException e) {
        LOGGER.info("Get ArrayIndexOutOfBoundsException: " + e.getMessage());
        return new ResponseMessage("Get ArrayIndexOutOfBoundsException: " + e.getMessage());
    }

    @ExceptionHandler(NoSuchReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handle(NoSuchReservationException e) {
        LOGGER.info("Couldn't find this reservation, check reservation number");
        return new ResponseMessage("Couldn't find this reservation, check reservation Number");
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handle(NoSuchUserException e) {
        LOGGER.info("Couldn't find this user, check user id and name");
        return new ResponseMessage("Couldn't find this user, check user id and name");
    }

    @ExceptionHandler(NoSuchRoomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handle(NoSuchRoomException e) {
        LOGGER.info("Couldn't find this room, check room number and id");
        return new ResponseMessage("Couldn't find this room, check room number and id");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Set<String>> handle(ConstraintViolationException e) {
        LOGGER.info(e.getMessage());
        return e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(ConstraintViolation::getMessage,
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toSet())));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handle(Exception e) {
        LOGGER.info("Get unknown exception " + e.getMessage());
        return new ResponseMessage("Get unknown exception " + e.getMessage());
    }
}
