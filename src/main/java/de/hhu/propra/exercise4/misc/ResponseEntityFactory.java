package de.hhu.propra.exercise4.misc;

import de.hhu.propra.exercise4.model.entity.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {

    private static ResponseEntity notFound(Exception e){
        return new ResponseEntity(new ErrorMessage(e.toString()), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity createPostResponseWithLocation(Boolean success, String location, Exception exception){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", location);

        if(success)
            return new ResponseEntity(headers, HttpStatus.CREATED);
        else
            return new ResponseEntity(new ErrorMessage(exception.toString()), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> createGetResponse(T entity, boolean success, Exception exception){
        if (success)
            return new ResponseEntity<T>(entity, HttpStatus.OK);
        else
            return notFound(exception);
    }

    public static ResponseEntity createDeleteResponse(boolean success, Exception exception){
        if(success)
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return notFound(exception);
    }
}
