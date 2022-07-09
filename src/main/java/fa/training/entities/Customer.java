package fa.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "createCustomer")
@ToString
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_Id")
    private Integer customerId;

    @Column(name = "Customer_Name")
    @NonNull
    private String customerName;

    @Column(name = "Phone", length = 10)
    @NonNull
    private String phone;

    @Column(name = "Birth_Date", columnDefinition = "date", length = 11)
    @NonNull
    private LocalDate birthDate;

    @Column(name = "Address", length = 500)
    @NonNull
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @NonNull
    @ToString.Exclude
    Set<Bill> bills = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Customer customer = (Customer) o;
        return customerId != null && Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
