package services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.RecipeRepository;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
}
