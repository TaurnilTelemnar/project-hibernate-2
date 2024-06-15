package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Year;
import java.util.Collection;


@Entity
@Table(name = "film")
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
    private Long filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    @ToString.Exclude
    private String description;

    @Column(name = "release_year")
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
    private Long rentalDuration;

    @Column(name = "rental_rate")
    @ToString.Exclude
    private Double rentalRate;

    @Column(name = "length")
    @ToString.Exclude
    private Long length;

    @Column(name = "replacement_cost")
    @ToString.Exclude
    private Double replacementCost;

    @Column(name = "rating")
    //@Enumerated(EnumType.STRING)
    private String rating;

    @Column(name = "special_features")
    @ToString.Exclude
    private String specialFeatures;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    private Collection<Category> filmCategories;

    @ManyToMany()
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @ToString.Exclude
    private Collection<Actor> filmActors;
}
