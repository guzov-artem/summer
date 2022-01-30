package com.vtr.camera.services;

import com.vtr.camera.entity.Image;
import com.vtr.camera.entity.Statistics;

import java.time.ZonedDateTime;
import java.util.List;

public interface DatabaseService {

    Image addImage(Image image);

    Image getLastImage();

    void deleteImageByName(String name) throws NullPointerException;

    void deleteImagesByInterval(ZonedDateTime time1, ZonedDateTime time2);

    List<Image> getAllImages(int pageNumber, ZonedDateTime time1, ZonedDateTime time2);

    List<Image> getAllImages(int pageNumber);

    Statistics getStatistics(ZonedDateTime time1, ZonedDateTime time2);

}
