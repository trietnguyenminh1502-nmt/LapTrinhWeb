package com.example.entity;

import java.awt.List;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Category")
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String images;
    private int status;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "category")
    private List videos;
}