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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "createEmployee")
@ToString
public class Employee implements Serializable {
    @Id
    @Column(name = "Employee_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    @Column(name = "Employee_Name")
    @NonNull
    private String employeeName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
                                                  CascadeType.MERGE,
                                                  CascadeType.REFRESH})
    @JoinColumn(name = "Role_Id")
    @NonNull
    @ToString.Exclude
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.DETACH,
                                                 CascadeType.MERGE,
                                                 CascadeType.REFRESH,
                                                 CascadeType.PERSIST})
    @ToString.Exclude
    Set<Bill> bills = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Employee employee = (Employee) o;
        return employeeId != null && Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
