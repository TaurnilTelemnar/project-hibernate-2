package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "movie",name = "film_text")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FilmText {
    @Id
    @JoinColumn(name = "film_id")
    @OneToOne()
    private Film film;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "title")
    private String title;
}
