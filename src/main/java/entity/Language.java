package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(schema = "movie",name = "language")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Language {

    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Byte
    private Long languageId;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    @Column(name = "name", columnDefinition = "char")
    private String name;

}
