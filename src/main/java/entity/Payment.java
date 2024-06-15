package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(schema = "movie",name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Short
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @ToString.Exclude
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "rental_id")
    @ToString.Exclude
    private Rental rental;

    @Column(name = "amount")
    //BigDecimal
    private Double amount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;
}
