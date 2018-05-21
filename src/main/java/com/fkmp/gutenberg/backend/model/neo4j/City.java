package com.fkmp.gutenberg.backend.model.neo4j;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.geo.Point;

@NodeEntity
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
