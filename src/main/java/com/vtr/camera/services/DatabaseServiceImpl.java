package com.vtr.camera.services;

import com.vtr.camera.Utils;
import com.vtr.camera.entity.Image;
import com.vtr.camera.entity.Statistics;
import com.vtr.camera.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class DatabaseServiceImpl implements DatabaseService {

    int pageSize = 30;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteImageByName(String name) throws NullPointerException {
        Image image = imageRepository.findByName(name).orElseThrow(NullPointerException::new);
        imageRepository.deleteById(image.getId());
    }

    @Override
    public void deleteImagesByInterval(ZonedDateTime time1, ZonedDateTime time2) {
        imageRepository.deleteByInterval(time1, time2);
    }

    @Override
    public Image getLastImage() {
        return imageRepository.getLastImage();
    }

    @Override
    public List<Image> getAllImages(int pageNumber) {

        long count = imageRepository.count();
        long numberOfPages = (int) Math.ceil(count / (double) pageSize);

        if (pageNumber > numberOfPages)
            return new ArrayList<>();

        long startOfSampling = (numberOfPages - pageNumber) * pageSize;
        return new ArrayList<>(imageRepository.findAllWithPagination(startOfSampling, pageSize));
    }

    @Override
    public List<Image> getAllImages(int pageNumber, ZonedDateTime time1, ZonedDateTime time2) {

        long count = imageRepository.getCountOfRecords(time1, time2);
        long numberOfPages = (int) Math.ceil(count / (double) pageSize);

        if (pageNumber > numberOfPages)
            return new ArrayList<>();

        long startOfSampling = (numberOfPages - pageNumber) * pageSize;
        return new ArrayList<>(imageRepository.findAllWithPaginationByInterval(time1, time2, startOfSampling, pageSize));
    }

    @Override
    public Statistics getStatistics(ZonedDateTime time1, ZonedDateTime time2) {

        long size = imageRepository.getSumOfSize(time1, time2);
        int count = imageRepository.getCountOfRecords(time1, time2);

        return new Statistics(Utils.convertBytesToMB(size), count);
    }
}
