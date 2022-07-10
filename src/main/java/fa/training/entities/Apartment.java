package fa.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Apartment", indexes = {@Index(name = "idx_apartment_status", columnList = "Status"),
                                      @Index(name = "idx_apartment_code", columnList = "Apartment_Code"),
                                      @Index(name = "idx_apartment_bedroom", columnList = "Num_Bedroom")})
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "createApartment")
@ToString
public class Apartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_id")
    private Integer apartmentId;

    @Column(name = "Apartment_Code", unique = true)
    @NonNull
    private String apartmentCode;

    @Column(name = "Num_Bedroom")
    @NonNull
    private Integer numBedroom;

    @Column(name = "Door_Direction")
    @NonNull
    private String doorDirection;

    @Column(name = "Price")
    @NonNull
    private Integer price;

    @Column(name = "Status")
    @NonNull
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Apartment apartment = (Apartment) o;
        return apartmentId != null && Objects.equals(apartmentId, apartment.apartmentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Status {
        AVAILABLE("available"), NOT_AVAILABLE("not_available");

        private final String value;
        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
