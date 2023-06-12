package com.bikkadit.elcetronicstore.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "CATEGORIES")
public class Category extends CustomFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer categoryId;

    @Column(name = "TITLE")
    private String categoryTitle;

    @Column(name = "DESCRIPTION")
    private String categoryDescription;

    @Column(name = "COVER_IMAGE")
    private String coverImage;


}
