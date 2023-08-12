package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Theater extends BaseModel {
    private String name;

    @ManyToOne
    private Region region;

    @OneToMany
    @JoinColumn(name = "theater_id") // Specify the foreign key column in the Screen table
    private List<Screen> screens;
}
