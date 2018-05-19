package com.fkmp.gutenberg.backend.model.postgres;

import org.springframework.data.geo.Point;

import javax.persistence.Id;

public class City {

    private String name;

    @Id
    private Point location;

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
