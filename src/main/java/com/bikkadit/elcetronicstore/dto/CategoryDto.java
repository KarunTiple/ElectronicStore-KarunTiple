package com.bikkadit.elcetronicstore.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto extends CustomFieldsDto {

    private String categoryId;

    @NotEmpty
    @Size(max = 10, message = "Title should be of 10 Characters")
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;

    private String coverImage;


}
