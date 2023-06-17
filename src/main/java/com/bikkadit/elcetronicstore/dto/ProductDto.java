package com.bikkadit.elcetronicstore.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends CustomFieldsDto{

    private String productId;

    @NotEmpty(message = "Title should not be Empty")
    private String title;

    @Size(max = 10000, message = "Length of the Description should not be greater than 10000 !!!")
    private String  description;

    @NotEmpty(message = "Brand should not be Empty")
    private String brand;

    @NotEmpty(message = "Price should not be Empty")
    private double price;

    @NotEmpty(message = "Disounted Price should not be Empty")
    private double discountedPrice;

    @NotEmpty(message = "Quantity should not be Empty")
    private Integer quantity;

    @NotEmpty(message = "Stock should not be Empty")
    private boolean stock;

    @NotEmpty(message = "Live should not be Empty")
    private boolean live;

    private String productImage;
}
