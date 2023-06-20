package com.tugasakhir.model.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequest {
    private String sequence;
    private String menu_name;
    private String path;
    private String icon;
    private Integer parent_id;
    private Integer menu_id;
    private Boolean menu_active;
}
