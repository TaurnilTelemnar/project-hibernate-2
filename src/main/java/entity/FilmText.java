package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film_text")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FilmText {
    @Id
    @JoinColumn(name = "film_id")
    @OneToOne(targetEntity = Film.class)
    private Film film;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;
}
