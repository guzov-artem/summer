package com.vtr.camera.controllers;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.vtr.camera.entity.ImageView;
import com.vtr.camera.entity.Statistics;
import com.vtr.camera.services.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class MainController {
    private ImageStoreService imageStoreService;

    @Autowired
    public MainController(ImageStoreService imageStoreService) {
        this.imageStoreService = imageStoreService;
    }


    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE, params={"page", "begin", "end"})
    ResponseEntity<List<ImageView>> getImagesByInterval(@RequestParam(value = "page")  int page,
                                                @RequestParam(value = "begin")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime begin,
                                                @RequestParam(value = "end")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        try {
            return ResponseEntity.ok(imageStoreService.getAllImages(page, begin, end));
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE, params={"page"})
    ResponseEntity<List<ImageView>> getImagesByInterval(@RequestParam(value = "page")  int page) {
        try {
            return ResponseEntity.ok(imageStoreService.getAllImages(page));
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    @GetMapping(value  = "last", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ImageView> getLastImageRecording() {
        try {
            ImageView lastImage = imageStoreService.getLastImage();
            return ResponseEntity.ok(lastImage);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("download/{name}")
    @ResponseBody
    ResponseEntity<byte []> downloadImage(@PathVariable String name) {
        try {
            byte[] byteImage = imageStoreService.downloadImage(name);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(byteImage.length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(byteImage);
        }
        catch (FileNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File wasn`t found in FileStore", exception);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "delete/{name}")
    @ResponseBody
    ResponseEntity<String> deleteImage(@PathVariable String name) {
        try {
            imageStoreService.deleteImageByName(name);
            return new ResponseEntity<String>("Deleted", HttpStatus.OK);
        }
        catch (FileNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File wasn`t found in FileStore", exception);
        }
        catch(NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recording wasn`t found in Database", exception);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "delete")
    ResponseEntity<String> deleteImages(@RequestParam(value = "begin")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime begin,
                                        @RequestParam(value = "end")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        try {
            imageStoreService.deleteImageByTime(begin, end);
            return new ResponseEntity<String>("Deleted", HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping(value = "statistic", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Statistics> getStatistic(@RequestParam(value = "begin")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime begin,
                                            @RequestParam(value = "end")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        try {
            return ResponseEntity.ok(imageStoreService.getStatistics(begin, end));
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
