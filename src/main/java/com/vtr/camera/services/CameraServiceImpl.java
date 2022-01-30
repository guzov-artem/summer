package com.vtr.camera.services;

import com.vtr.camera.configurations.Config;
import com.vtr.camera.entity.CameraData;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class CameraServiceImpl implements CameraService {

    private Config configProperties;
    private VideoCapture capture;

    @Autowired
    public CameraServiceImpl(Config configProperties) {
        this.configProperties = configProperties;
        OpenCV.loadLocally();
        System.load("C:\\opencv\\build\\bin\\opencv_videoio_ffmpeg451_64.dll");
        capture = new VideoCapture();
    }

    public CameraServiceImpl() {
    }

    public CameraData readImage() {
        Mat matrix = new Mat();
        capture.open(configProperties.getUrl());
        capture.read(matrix);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        return new CameraData(byteArray, ZonedDateTime.now());
    }

}
