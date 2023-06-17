package com.bikkadit.elcetronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCTS")
public class Products extends CustomFields {

    @Id
    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "PRODUCT_TITLE")
    private String title;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "DISCOUNTED_PRICE")
    private double discountedPrice;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "STOCK")
    private boolean stock;

    @Column(name = "LIVE")
    private boolean live;

    @Column(name = "PRODUCT_IMAGE")
    private String productImage;


}
