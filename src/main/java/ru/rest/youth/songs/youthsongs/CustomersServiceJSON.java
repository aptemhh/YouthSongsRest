package ru.rest.youth.songs.youthsongs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class CustomersServiceJSON {

    // get by id service
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") String id) {

        return Response.ok().entity("sdf"+id+ new String(" dsad")).build();
    }

}