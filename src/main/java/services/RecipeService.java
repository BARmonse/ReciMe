package services;

import dtos.RecipeCreationDTO;
import dtos.RecipeSearchDTO;
import lombok.AllArgsConstructor;
import models.Recipe;
import org.springframework.stereotype.Service;
import repositories.RecipeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    public List<Recipe> searchRecipes(RecipeSearchDTO dto) {
        return recipeRepository.searchRecipes(
                dto.getVegetarian(),
                dto.getServings(),
                dto.getIncludedIngredients(),
                dto.getExcludedIngredients(),
                dto.getInstructionSearch()
        );
    }

    public Recipe createRecipe(RecipeCreationDTO recipeCreationDTO) {
        return recipeRepository.save(Recipe.fromDTO(recipeCreationDTO));
    }

    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        Recipe recipe = getRecipeById(id);
        recipe.setTitle(updatedRecipe.getTitle());
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setIngredients(updatedRecipe.getIngredients());
        recipe.setInstructions(updatedRecipe.getInstructions());
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
