package ru.rest.youth.songs.youthsongs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.rest.youth.songs.youthsongs.data.JdbcSongDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class CustomersServiceJSON {

    // get by id service
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") Integer id) {
        ApplicationContext context =
                    new ClassPathXmlApplicationContext("META-INF/spring/dao.xml");
        JdbcSongDAO jdbcSongDAO = (JdbcSongDAO) context.getBean("SongDAO");

        return Response.ok().entity(jdbcSongDAO.findBySongNumber(id).getText()).build();
    }

}