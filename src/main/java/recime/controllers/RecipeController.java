package recime.controllers;

import recime.dtos.RecipeCreationDTO;
import recime.dtos.RecipeSearchDTO;
import lombok.AllArgsConstructor;
import recime.models.Recipe;
import org.springframework.web.bind.annotation.*;
import recime.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/recipe")
    public Recipe getRecipeByName(@RequestParam String name) { 
        return recipeService.getRecipeByName(name); 
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@ModelAttribute RecipeSearchDTO dto) { 
        return recipeService.searchRecipes(dto); 
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody RecipeCreationDTO recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
} 