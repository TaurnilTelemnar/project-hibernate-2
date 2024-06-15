package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class Rental {

    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    @Column(name = "rental_date")
    private Date rentalDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @ToString.Exclude
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @ToString.Exclude
    private Staff staff;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

}
