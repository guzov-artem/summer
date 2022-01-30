package com.vtr.camera.services;

import com.vtr.camera.entity.CameraData;
import com.vtr.camera.entity.Image;
import com.vtr.camera.entity.ImageView;

import com.vtr.camera.entity.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageStoreServiceImpl implements ImageStoreService {


    private final FileStoreService fileStoreService;
    private final DatabaseService databaseService;
    private final UrlGenerator urlGenerator;

    @Autowired
    public ImageStoreServiceImpl(FileStoreService fileStoreService, DatabaseService databaseService, UrlGenerator urlGenerator) {
        this.fileStoreService = fileStoreService;
        this.databaseService = databaseService;
        this.urlGenerator = urlGenerator;
    }

    public void addImage(CameraData cameraData) throws FileSystemException {
        Image image = fileStoreService.addImageToFileSystem(cameraData);
        databaseService.addImage(image);
    }

    public List<ImageView> getAllImages(int pageNumber) {
        return databaseService.getAllImages(pageNumber).stream()
                .map(image -> new ImageView(image, urlGenerator.getImageUrlByName(image.getName())))
                .collect(Collectors.toList());
    }

    public List<ImageView> getAllImages(int pageNumber, ZonedDateTime time1, ZonedDateTime time2) {
        return databaseService.getAllImages(pageNumber, time1, time2)
                .stream()
                .map(image -> new ImageView(image, urlGenerator.getImageUrlByName(image.getName())))
                .collect(Collectors.toList());
    }

    public ImageView getLastImage() {
        Image image = databaseService.getLastImage();
        return new ImageView(image, urlGenerator.getImageUrlByName(image.getName()));
    }

    public byte[] downloadImage(String name) throws FileNotFoundException {
        return fileStoreService.downloadImageFromFileSystem(name);
    }

    public void deleteImageByName(String name) throws FileNotFoundException, NullPointerException {
        fileStoreService.deleteImageFromFileSystem(name);
        databaseService.deleteImageByName(name);
    }

    public void deleteImageByTime(ZonedDateTime time1, ZonedDateTime time2) {
        fileStoreService.deleteImagesByIntervalFromFileSystem(time1, time2);
        databaseService.deleteImagesByInterval(time1, time2);
    }

    public Statistics getStatistics(ZonedDateTime time1, ZonedDateTime time2) {
        return databaseService.getStatistics(time1, time2);
    }
}
