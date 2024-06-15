package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    @Column(name = "city")
    private String city;
}
