package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(schema = "movie",name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Staff {

    @Id
    @Column(name = "staff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Byte
    private Long staffId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @Lob
    @ToString.Exclude
    @Column(name = "picture", columnDefinition = "blob")
    private byte[] picture;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @ToString.Exclude
    private Store store;

    @Column(name = "active", columnDefinition = "BIT")
    private Boolean isActive;

    @Column(name = "username")
    @ToString.Exclude
    private String username;

    @Column(name = "password")
    @ToString.Exclude
    private String password;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Timestamp lastUpdate;

}
