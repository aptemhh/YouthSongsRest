package ru.rest.youth.songs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.rest.youth.songs.data.JdbcSongDAO;
import ru.rest.youth.songs.json.JsonConverter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class RestService  {

    private static ApplicationContext context =
            new ClassPathXmlApplicationContext("META-INF/spring/dao.xml");
    private static JdbcSongDAO jdbcSongDAO = (JdbcSongDAO) context.getBean("SongDAO");

    @Value("${uploadOnline}")
    private Boolean uploadOnline;

    @GET
    @Path("/number/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSong(@PathParam("id") Integer id) {
        return JsonConverter.convert(jdbcSongDAO.findBySongNumber(id));
    }

    @GET
    @Path("/find/{text}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findSongs(@PathParam("text") String text) {
        return JsonConverter.convert(jdbcSongDAO.findSongByText("2"));
    }

    @GET
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadXml() {
        if (uploadOnline) {
            //#TODO релизовать загрузку через xml файл
        }
        return "" + uploadOnline;
    }
}