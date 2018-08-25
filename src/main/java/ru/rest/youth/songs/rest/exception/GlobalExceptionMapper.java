package ru.rest.youth.songs.rest.exception;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {


    public Response toResponse(Throwable exception){
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}