package com.vtr.camera.services;


import com.vtr.camera.entity.CameraData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystemException;
import java.time.ZonedDateTime;

@Service
@EnableScheduling
public class CameraSchedulerServiceImpl implements CameraSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(CameraSchedulerServiceImpl.class);
    private final CameraService cameraService;
    private final ImageStoreService imageStoreService;

    @Value("${camera.startPauseTime}")
    private String startTimeStop;

    @Value("${camera.endPauseTime}")
    private String endTimeStop;

    @Autowired
    public CameraSchedulerServiceImpl(CameraService cameraService, ImageStoreService imageStoreService) {
        this.cameraService = cameraService;
        this.imageStoreService = imageStoreService;
    }

    @Scheduled(fixedRateString = "${camera.period}000")
    public void schedulingReadImage() {
        CameraData cameraData = cameraService.readImage();
        if (checkTime(cameraData.getDate())) {
            try {
                imageStoreService.addImage(cameraData);
            } catch (FileSystemException e) {
                logger.error(e.getMessage());
            }
        }
    }

    boolean checkTime(ZonedDateTime zonedDateTime) {
        if (startTimeStop.isEmpty() && endTimeStop.isEmpty()) {
            return true;
        } else {
            int startHour = Integer.parseInt(startTimeStop.substring(0, 2));
            int startMinute = Integer.parseInt(startTimeStop.substring(3));

            int endHour = Integer.parseInt(endTimeStop.substring(0, 2));
            int endMinute = Integer.parseInt(endTimeStop.substring(3));

            ZonedDateTime tempStartDate = ZonedDateTime.now();
            tempStartDate = tempStartDate.withHour(startHour);
            tempStartDate = tempStartDate.withMinute(startMinute);
            tempStartDate = tempStartDate.withSecond(0);

            ZonedDateTime tempEndDate = ZonedDateTime.now();
            tempEndDate = tempEndDate.withHour(endHour);
            tempEndDate = tempEndDate.withMinute(endMinute);
            tempEndDate = tempEndDate.withSecond(0);

            return (zonedDateTime.compareTo(tempStartDate) <= 0) || (zonedDateTime.compareTo(tempEndDate) >= 0);
        }
    }
}


