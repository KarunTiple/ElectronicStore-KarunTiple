package com.bikkadit.elcetronicstore.dto;

import com.bikkadit.elcetronicstore.config.AppConstants;
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

    @NotEmpty(message = AppConstants.TITLE_EMPTY)
    private String title;

    @Size(max = 10000, message =AppConstants.DESCRIPTION_LENGTH )
    private String  description;

    @NotEmpty(message = AppConstants.BRAND_EMPTY)
    private String brand;

    @NotEmpty(message =AppConstants.PRICE_EMPTY )
    private double price;

    @NotEmpty(message = AppConstants.DISCOUNTED_PRICE_EMPTY)
    private double discountedPrice;

    @NotEmpty(message = AppConstants.QUANTITY_EMPTY)
    private Integer quantity;

    @NotEmpty(message =AppConstants.STOCK_EMPTY )
    private boolean stock;

    @NotEmpty(message =AppConstants.LIVE_EMPTY )
    private boolean live;

    private String productImage;
}
