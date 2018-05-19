package com.fkmp.gutenberg.backend.model.postgres;

import com.fkmp.gutenberg.backend.model.AbstractCity;
import org.springframework.data.geo.Point;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class City extends AbstractCity {

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
