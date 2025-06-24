package recime.services;

import recime.dtos.RecipeCreationDTO;
import recime.dtos.RecipeSearchDTO;
import lombok.AllArgsConstructor;
import recime.models.Recipe;
import org.springframework.stereotype.Service;
import recime.repositories.RecipeRepository;

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

    public Recipe getRecipeByName(String name) {
        return recipeRepository.findByTitle(name).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    public List<Recipe> searchRecipes(RecipeSearchDTO dto) {
        String includedIngredientsStr = null;
        String excludedIngredientsStr = null;
        
        if (dto.getIncludedIngredients() != null && !dto.getIncludedIngredients().isEmpty()) {
            // If it's a single string with spaces, use it as is
            if (dto.getIncludedIngredients().size() == 1 && dto.getIncludedIngredients().get(0).contains(" ")) {
                includedIngredientsStr = dto.getIncludedIngredients().get(0);
            } else {
                // Multiple ingredients, join with spaces
                includedIngredientsStr = String.join(" ", dto.getIncludedIngredients());
            }
        }
        
        if (dto.getExcludedIngredients() != null && !dto.getExcludedIngredients().isEmpty()) {
            // If it's a single string with spaces, use it as is
            if (dto.getExcludedIngredients().size() == 1 && dto.getExcludedIngredients().get(0).contains(" ")) {
                excludedIngredientsStr = dto.getExcludedIngredients().get(0);
            } else {
                // Multiple ingredients, join with spaces
                excludedIngredientsStr = String.join(" ", dto.getExcludedIngredients());
            }
        }
        
        return recipeRepository.searchRecipes(
                dto.getVegetarian(),
                dto.getServings(),
                includedIngredientsStr,
                excludedIngredientsStr,
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
