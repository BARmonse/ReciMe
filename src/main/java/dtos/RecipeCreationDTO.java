package dtos;

import lombok.Data;

import java.util.List;

@Data
public class RecipeCreationDTO {
    private String title;
    private String description;
    private List<String> ingredients;
    private String instructions;
    private Boolean vegetarian;
    private Integer servings;
}

