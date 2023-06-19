package com.bikkadit.elcetronicstore.dto;

import com.bikkadit.elcetronicstore.config.AppConstants;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends CustomFieldsDto {

    private String productId;

    @NotEmpty(message = AppConstants.TITLE_EMPTY)
    private String title;

    @Size(max = 10000, message = AppConstants.DESCRIPTION_LENGTH)
    private String description;

    @NotEmpty(message = AppConstants.BRAND_EMPTY)
    private String brand;

    @NotNull(message = AppConstants.PRICE_EMPTY)
    private double price;

    @NotNull(message = AppConstants.DISCOUNTED_PRICE_EMPTY)
    private double discountedPrice;

    @NotNull(message = AppConstants.QUANTITY_EMPTY)
    private Integer quantity;

    @NotNull(message = AppConstants.STOCK_EMPTY)
    private boolean stock;

    @NotNull(message = AppConstants.LIVE_EMPTY)
    private boolean live;

    private String productImage;
}
