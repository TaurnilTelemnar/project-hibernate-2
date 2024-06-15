package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(schema = "movie",name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Byte
    private Long storeId;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "manager_staff_id")
    private Staff managerStaff;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

}
