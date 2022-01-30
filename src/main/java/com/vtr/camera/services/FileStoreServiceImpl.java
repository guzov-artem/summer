package com.vtr.camera.services;

import com.vtr.camera.entity.CameraData;
import com.vtr.camera.entity.Image;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class FileStoreServiceImpl implements FileStoreService {
    private static final String IMAGE_NAME_FORMAT = "yyyyMMdd_HHmmss";
    private static final String DIRECTORY = "images/";
    private static final String IMAGE_FORMAT = ".jpg";
    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern(IMAGE_NAME_FORMAT);

    public static String formImageName(ZonedDateTime time) {
        String formatDate = PATTERN.format(time);
        return formatDate + IMAGE_FORMAT;
    }

    public Image addImageToFileSystem(CameraData cameraData) throws FileSystemException {
        String name = formImageName(cameraData.getDate());
        Path path = Paths.get(DIRECTORY + name);
        int size = 0;
        try {
            Files.write(path, cameraData.getData());
            size = (int) Files.size(path);
        } catch (IOException e) {
            throw new FileSystemException("Could not write file " + path + ".");
        }

        return new Image(name, size, cameraData.getDate());
    }

    public void deleteImageFromFileSystem(String name) throws FileNotFoundException {
        Path path = Paths.get(DIRECTORY + name);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileNotFoundException("Could not delete file " + path + ".");
        }
    }

    public void deleteImagesByIntervalFromFileSystem(ZonedDateTime time1, ZonedDateTime time2) {
        while (!(time1.isAfter(time2))) {
            try {
                deleteImageFromFileSystem(formImageName(time1));
            } catch (FileNotFoundException ignored) {
            } finally {
                time1 = time1.plus(1, ChronoUnit.SECONDS);
            }
        }
    }

    public byte[] downloadImageFromFileSystem(String name) throws FileNotFoundException {
        Path path = Paths.get(DIRECTORY + name);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FileNotFoundException("Could not read file " + path + ".");
        }
    }
}
