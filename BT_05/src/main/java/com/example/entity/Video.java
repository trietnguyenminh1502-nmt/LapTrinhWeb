package com.example.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Videos")
@Data
public class Video implements Serializable {
    @Id
    private String videoId;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
