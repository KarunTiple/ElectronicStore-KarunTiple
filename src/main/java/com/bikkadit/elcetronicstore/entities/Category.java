package com.bikkadit.elcetronicstore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CATEGORIES")
public class Category extends CustomFields {

    @Id
    @Column(name = "ID")
    private String categoryId;

    @Column(name = "TITLE")
    private String categoryTitle;

    @Column(name = "DESCRIPTION")
    private String categoryDescription;

    @Column(name = "COVER_IMAGE")
    private String coverImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<>();


}
