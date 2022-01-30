package com.vtr.camera.repository;

import com.vtr.camera.entity.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

    @Query(value = "select * from cameradb_table where name = :name",
            nativeQuery = true)
    Optional<Image> findByName(@Param("name") String name);


    @Query(value = "select * from cameradb_table order by time_read desc limit 1",
            nativeQuery = true)
    Image getLastImage();


    @Modifying
    @Query(value = "delete from cameradb_table where time_read > :time1 and time_read < :time2",
            nativeQuery = true)
    void deleteByInterval(@Param("time1") ZonedDateTime time1, @Param("time2") ZonedDateTime time2);


    @Query(value = "select * from cameradb_table order by time_read desc limit :size offset :start",
            nativeQuery = true)
    List<Image> findAllWithPagination(@Param("start") long startOfSampling, @Param("size") int pageSize);


    @Query(value = "select * from cameradb_table " +
            "where time_read > :time1 and time_read < :time2 order by time_read desc limit :size offset :start",
            nativeQuery = true)
    List<Image> findAllWithPaginationByInterval(@Param("time1") ZonedDateTime time1, @Param("time2") ZonedDateTime time2,
                                                @Param("start") long startOfSampling, @Param("size") int pageSize);


    @Query(value = "select count(*)" +
            "from (select * from cameradb_table where time_read > :time1 and time_read < :time2) filtered_data",
            nativeQuery = true)
    int getCountOfRecords(@Param("time1") ZonedDateTime time1, @Param("time2") ZonedDateTime time2);


    @Query(value = "select sum(size) from cameradb_table where time_read > :time1 and time_read < :time2",
            nativeQuery = true)
    long getSumOfSize(@Param("time1") ZonedDateTime time1, @Param("time2") ZonedDateTime time2);
}
