package com.vtr.camera.services;

import com.vtr.camera.entity.CameraData;
import com.vtr.camera.entity.Image;
import com.vtr.camera.entity.ImageView;

import com.vtr.camera.entity.Statistics;
import javassist.NotFoundException;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.time.ZonedDateTime;
import java.util.List;

public interface ImageStoreService {
    void addImage(CameraData cameraData) throws FileSystemException;

    List<ImageView> getAllImages(int pageNumber);

    List<ImageView> getAllImages(int pageNumber, ZonedDateTime time1, ZonedDateTime time2);

    ImageView getLastImage();

    byte[] downloadImage(String name) throws FileNotFoundException;

    void deleteImageByName(String name) throws FileNotFoundException, NullPointerException;

    void deleteImageByTime(ZonedDateTime time1, ZonedDateTime time2);

    public Statistics getStatistics(ZonedDateTime time1, ZonedDateTime time2);
}
