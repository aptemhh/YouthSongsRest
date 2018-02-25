package ru.rest.youth.songs.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {


    public Response toResponse(Throwable exception){
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}