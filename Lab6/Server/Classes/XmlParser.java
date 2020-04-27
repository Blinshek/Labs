package com.company;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class XmlParser {
    public static LinkedHashMap<Integer, Movie> parseXML() throws JAXBException, FileNotFoundException {
        String path = System.getenv("TEST");
        if(path == null){
            throw new FileNotFoundException();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(MovieMap.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        MovieMap movMap = (MovieMap) jaxbUnmarshaller.unmarshal(new File(path + "/films.xml"));
        //Обновляем maxID для будущих фильмов
        long maxID = 0;
        for (Map.Entry<Integer, Movie> entry : movMap.getMovieMap().entrySet())
            if (entry.getValue().getId() > maxID)
                maxID = entry.getValue().getId();
        Movie.setFreeID(maxID + 1);
        return movMap.getMovieMap();
    }
}