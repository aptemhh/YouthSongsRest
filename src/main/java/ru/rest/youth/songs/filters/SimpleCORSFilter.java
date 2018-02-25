package ru.rest.youth.songs.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class SimpleCORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "x-requested-with");
    }
}