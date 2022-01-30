package com.vtr.camera.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "cameradb_table")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "time_read")
    private ZonedDateTime zonedDateTime;

    public Image() {
    }

    public Image(String name, int size, ZonedDateTime zonedDateTime) {
        this.name = name;
        this.size = size;
        this.zonedDateTime = zonedDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

}
