package recime.models;

import recime.dtos.RecipeCreationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "recipes")
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> ingredients;

    @Column(columnDefinition = "text")
    private String instructions;

    private Boolean vegetarian;

    private Integer servings;

    public static Recipe fromDTO(RecipeCreationDTO dto) {
        return Recipe.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .ingredients(dto.getIngredients())
                .instructions(dto.getInstructions())
                .vegetarian(dto.getVegetarian())
                .servings(dto.getServings())
                .build();
    }
}
