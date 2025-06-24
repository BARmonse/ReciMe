package recime.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RecipeSearchDTO {
    private Boolean vegetarian;
    private Integer servings;
    private List<String> includedIngredients;
    private List<String> excludedIngredients;
    private String instructionSearch;
}
