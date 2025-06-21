package repositories;

import models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = """
    SELECT *
    FROM recipes
    WHERE (:vegetarian IS NULL OR vegetarian = :vegetarian)
      AND (:servings IS NULL OR servings = :servings)
      AND (:includedIngredients IS NULL OR ingredients @> CAST(:includedIngredients AS text[]))
      AND (:excludeIngredients IS NULL OR NOT (ingredients && CAST(:excludedIngredients AS text[])))
      AND (:instructionSearch IS NULL OR instructions ILIKE CONCAT('%', :instructionSearch, '%'))
    """, nativeQuery = true)
    List<Recipe> searchRecipes(
            @Param("vegetarian") Boolean vegetarian,
            @Param("servings") Integer servings,
            @Param("includedIngredients") List<String> includedIngredients,
            @Param("excludedIngredients") List<String> excludedIngredients,
            @Param("instructionSearch") String instructionSearch
    );


}
