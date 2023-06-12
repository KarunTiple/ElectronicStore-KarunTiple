package com.bikkadit.elcetronicstore.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CategoryDto extends  CustomFieldsDto{

    @NotEmpty
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;

    private String coverImage;


}
