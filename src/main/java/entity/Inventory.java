package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "film_id")
    @ToString.Exclude
    private Film film;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @ToString.Exclude
    private Store store;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "inventory_id")
    private Collection<Rental> rentals;

}
