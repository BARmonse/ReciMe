package recime.repositories;

import recime.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    Optional<Recipe> findByTitle(String name);

    @Query(value = """
    SELECT * FROM recipes 
    WHERE (:vegetarian IS NULL OR vegetarian = :vegetarian)
      AND (:servings IS NULL OR servings = :servings)
      AND (:instructionSearch IS NULL OR LOWER(instructions) LIKE CONCAT('%', LOWER(:instructionSearch), '%'))
      AND (:includedIngredients IS NULL OR (
          SELECT COUNT(*) = array_length(string_to_array(:includedIngredients, ' '), 1)
          FROM unnest(string_to_array(:includedIngredients, ' ')) AS search_term
          WHERE EXISTS (
              SELECT 1 FROM unnest(ingredients) AS ingredient 
              WHERE LOWER(ingredient) LIKE CONCAT('%', LOWER(search_term), '%')
          )
      ))
      AND (:excludedIngredients IS NULL OR NOT EXISTS (
          SELECT 1 FROM unnest(string_to_array(:excludedIngredients, ' ')) AS search_term
          WHERE EXISTS (
              SELECT 1 FROM unnest(ingredients) AS ingredient 
              WHERE LOWER(ingredient) LIKE CONCAT('%', LOWER(search_term), '%')
          )
      ))
    """, nativeQuery = true)
    List<Recipe> searchRecipes(
            @Param("vegetarian") Boolean vegetarian,
            @Param("servings") Integer servings,
            @Param("includedIngredients") String includedIngredients,
            @Param("excludedIngredients") String excludedIngredients,
            @Param("instructionSearch") String instructionSearch
    );

}
