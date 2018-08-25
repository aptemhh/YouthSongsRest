package ru.rest.youth.songs.rest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import ru.rest.youth.songs.services.SongService;
import ru.rest.youth.songs.data.Song;
import ru.rest.youth.songs.services.StatisticDAO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class RestService {

    @Resource
    private SongService songService;
    private static StatisticDAO statisticDAO = new StatisticDAO();

//    @Value("${uploadOnline}")
//    private Boolean uploadOnline;

    @RequestMapping(value = "/number/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee", key = "#id")
    public Song getSong(@PathVariable("id") Long id) throws SQLException {
        statisticDAO.insert(id);
        return songService.findBySongNumber(id);
    }


    @RequestMapping(value = "/find/{text}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee", key = "#text")
    public List<Song> findSongs(@PathVariable("text") String text)
    {
        return songService.getSongListShort(text);
    }

    @RequestMapping(value = "/find")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee")
    public List<Song> findSongs()
    {
        return songService.getSongListShort();
    }


    @RequestMapping(value = "/upload")
    @Produces(MediaType.TEXT_HTML)
    @CacheEvict(value = "employee")
    public Response uploadXml() {
//        if (uploadOnline) {
//
//        }

        return Response.ok().build();
    }

    @RequestMapping(value = "/upload1")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @CacheEvict(value = "employee")
    public Response upload(Song song) {
//        if (uploadOnline) {
//
//            //#TODO релизовать загрузку через xml файл
//        }
        return Response.ok().build();
    }

    @RequestMapping(value = "/download")
    @Cacheable(value = "employee")
    @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
    public Response downloadXml(@Context HttpServletResponse response) throws IOException, JAXBException, SQLException {
        Response.ResponseBuilder builder = Response.ok();
        songService.getXmlAllSongs(response.getOutputStream());
        builder.header("Content-Disposition", "attachment; filename=qwe.xml");
        return builder.build();
    }

    @RequestMapping(value = "/party/{name}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Cacheable(value = "employee")
    public List<Song> findSongsByParty(@PathVariable("name") String name) throws SQLException {
        return songService.getSongListShortByParty(name);
    }
}