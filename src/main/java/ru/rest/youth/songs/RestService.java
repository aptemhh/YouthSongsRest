package ru.rest.youth.songs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.rest.youth.songs.data.JdbcSongDAO;
import ru.rest.youth.songs.json.JsonConverter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

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
        //#todo запилить валидацию
        return JsonConverter.convert(jdbcSongDAO.getSongList(text));
    }

    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    public Response uploadXml() {
        if (uploadOnline) {
            //#TODO релизовать загрузку через xml файл
        }

        return Response.ok().build();
    }

    @GET
    @Path("/download")
    @Produces(MediaType.TEXT_HTML)
    public Response downloadXml(@Context HttpServletResponse response) throws IOException {
        Response.ResponseBuilder builder = Response.ok();
        jdbcSongDAO.getXmlAllSongs(response.getOutputStream());
        builder.header("Content-Disposition", "attachment; filename=qwe.xml" );
        return builder.build();
    }
}