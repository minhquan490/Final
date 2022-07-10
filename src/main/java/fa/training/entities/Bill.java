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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Bill")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bill_Id")
    private Integer billId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
                                                  CascadeType.PERSIST,
                                                  CascadeType.MERGE,
                                                  CascadeType.REFRESH})
    @JoinColumn(name = "Employee_Id")
    @ToString.Exclude
    @NonNull
    private Employee employee;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
                                                 CascadeType.PERSIST,
                                                 CascadeType.MERGE,
                                                 CascadeType.REFRESH})
    @JoinColumn(name = "Apartment_Id")
    @ToString.Exclude
    @NonNull
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
                                                  CascadeType.PERSIST,
                                                  CascadeType.MERGE,
                                                  CascadeType.REFRESH})
    @JoinColumn(name = "Customer_Id")
    @ToString.Exclude
    @NonNull
    private Customer customer;

    @Column(name = "Buy_Date", columnDefinition = "date")
    @NonNull
    private LocalDate buyDate;

    @Column(name = "Price")
    @NonNull
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Bill bill = (Bill) o;
        return billId != null && Objects.equals(billId, bill.billId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
