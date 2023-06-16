package com.bikkadit.elcetronicstore.entities;

import lombok.*;

import javax.persistence.*;

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


}
