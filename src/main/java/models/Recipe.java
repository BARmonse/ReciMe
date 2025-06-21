package models;

import dtos.RecipeCreationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ElementCollection
    private List<String> ingredients;

    @Lob
    private String instructions;

    public static Recipe fromDTO(RecipeCreationDTO dto) {
        return Recipe.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .ingredients(dto.getIngredients())
                .instructions(dto.getInstructions())
                .build();
    }
}
