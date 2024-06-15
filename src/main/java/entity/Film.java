package entity;

import converter.RatingConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@Entity
@Table(schema = "movie", name = "film")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Film {

    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Short
    private Long filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    @ToString.Exclude
    private String description;

    @Column(name = "release_year", columnDefinition = "year")
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @ToString.Exclude
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    @ToString.Exclude
    private Language originalLanguage;

    @Column(name = "rental_duration")
    @ToString.Exclude
    //Byte
    private Long rentalDuration;

    @Column(name = "rental_rate")
    @ToString.Exclude
    //BigDecimal
    private Double rentalRate;

    @Column(name = "length")
    @ToString.Exclude
    //Short
    private Long length;

    @Column(name = "replacement_cost")
    @ToString.Exclude
    //BigDecimal
    private Double replacementCost;

    @Column(name = "rating", columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    @ToString.Exclude
    private String specialFeatures;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    )
    @ToString.Exclude
    private Collection<Category> filmCategories;

    @ManyToMany()
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    )
    @ToString.Exclude
    private Collection<Actor> filmActors;


    public Set<Feature> getSpecialFeatures() {
        if (isNull(specialFeatures)) return null;

        return Arrays.stream(specialFeatures.split(","))
                .map(Feature::getFeatureByValue)
                .filter(f -> !isNull(f))
                .collect(Collectors.toSet());

    }

    public void setSpecialFeatures(Set<Feature> specialFeatures) {
        if (isNull(specialFeatures)) {
            this.specialFeatures = null;
            return;
        }

        this.specialFeatures = specialFeatures.stream()
                .map(Feature::getValue)
                .collect(Collectors.joining(","));

    }

    public static class FilmBuilder {
        public FilmBuilder specialFeatures(Set<Feature> specialFeatures) {
            if (isNull(specialFeatures)) {
                this.specialFeatures = null;
            } else {
                this.specialFeatures = specialFeatures.stream()
                        .map(Feature::getValue)
                        .collect(Collectors.joining(","));
            }
            return this;
        }
    }
}
