package ru.rest.youth.songs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.rest.youth.songs.data.JdbcSongDAO;
import ru.rest.youth.songs.data.Song;
import ru.rest.youth.songs.data.StatisticDAO;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class RestService {

    private static ApplicationContext context =
            new ClassPathXmlApplicationContext("META-INF/spring/dao.xml");
    private static JdbcSongDAO jdbcSongDAO = (JdbcSongDAO) context.getBean("SongDAO");
    private static StatisticDAO statisticDAO = (StatisticDAO) context.getBean("StatisticDAO");

    @Value("${uploadOnline}")
    private Boolean uploadOnline;

    @GET
    @Path("/number/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee", key = "#id")
    public Song getSong(@PathParam("id") Integer id) throws SQLException {
        statisticDAO.insert(id);
        return jdbcSongDAO.findBySongNumber(id);
    }

    @GET
    @Path("/find/{text}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee", key = "#text")
    public List<Song> findSongs(@PathParam("text") String text) throws SQLException {
        return jdbcSongDAO.getSongListShort(text);
    }

    @GET
    @Path("/find/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee")
    public List<Song> findSongs() throws SQLException {
        return jdbcSongDAO.getSongListShort(null);
    }


    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    @CacheEvict(value = "employee")
    public Response uploadXml() {
        if (uploadOnline) {

        }

        return Response.ok().build();
    }

    @GET
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @CacheEvict(value = "employee")
    public Response upload(Song song) {
        if (uploadOnline) {

            //#TODO релизовать загрузку через xml файл
        }
        return Response.ok().build();
    }

    @GET
    @Path("/download")
    @Cacheable(value = "employee")
    @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
    public Response downloadXml(@Context HttpServletResponse response) throws IOException, JAXBException, SQLException {
        Response.ResponseBuilder builder = Response.ok();
        jdbcSongDAO.getXmlAllSongs(response.getOutputStream());
        builder.header("Content-Disposition", "attachment; filename=qwe.xml");
        return builder.build();
    }

    @GET
    @Path("/party/{name}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee")
    public List<Song> findSongsByParty(@PathParam("name") String name) throws SQLException {
        return jdbcSongDAO.getSongListShortByParty(name);
    }
}