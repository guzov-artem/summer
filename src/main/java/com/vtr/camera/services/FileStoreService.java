package com.vtr.camera.services;

import com.vtr.camera.entity.CameraData;
import com.vtr.camera.entity.Image;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.time.ZonedDateTime;

public interface FileStoreService {

    Image addImageToFileSystem(CameraData cameraData) throws FileSystemException;

    void deleteImageFromFileSystem(String name) throws FileNotFoundException;

    void deleteImagesByIntervalFromFileSystem(ZonedDateTime time1, ZonedDateTime time2);

    byte[] downloadImageFromFileSystem(String name) throws FileNotFoundException;
}
