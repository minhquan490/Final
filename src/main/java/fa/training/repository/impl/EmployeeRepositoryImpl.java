package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.entities.Employee;
import fa.training.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static fa.training.annotation.resolver.InjectResolver.inject;
import static fa.training.annotation.resolver.InjectType.SESSION;

@Log4j2
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Employee employee) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.save(employee);
            log.info("Persist " + employee.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
            transaction.commit();
            log.info("Commit transaction at: " + LocalDateTime.now());
            return true;
        } catch (HibernateException | IllegalStateException | RollbackException e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Commit transaction and save change to database at: " + LocalDateTime.now());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    public boolean edit(Employee employee) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.update(employee);
            log.info("Update " + employee.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
            transaction.commit();
            log.info("Commit transaction and save change to database at: " + LocalDateTime.now());
            return true;
        } catch (HibernateException | IllegalStateException | RollbackException e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Roll back transaction at: " + LocalDateTime.now());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    public boolean delete(Employee employee) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.delete(employee);
            log.info("Delete " + employee.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
            transaction.commit();
            log.info("Commit transaction and save change to database at: " + LocalDateTime.now());
            return true;
        } catch (HibernateException | IllegalStateException | RollbackException e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            log.info("Roll back transaction at: " + LocalDateTime.now());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    public Employee get(int employeeId) {
        String hql = "select e from Employee e where e.employeeId = :employeeId";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("employeeId", employeeId);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Employee) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    public Employee get(String employeeName) {
        String hql = "select e from Employee e where e.employeeName = :employeeName";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("employeeName", employeeName);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Employee) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Employee> get() {
        String hql = "select e from Employee e join fetch e.role r join fetch e.bills b where count(b.billId) < 3 and r.roleName = :roleName";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("roleName", "NV");
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Employee>) query.getResultStream().collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Employee> getIfBillNull() {
        String hql = "select e from Employee e join fetch e.role r where r.roleName = :roleName";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("roleName", "NV");
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Employee>) query.getResultStream().collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Employee> getAll() {
        String hql = "select e from Employee e";
        inject(this);
        try {
            Query query = session.createQuery(hql);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Employee>) query.getResultStream().collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " is cause");
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("Close transaction at: " + LocalDateTime.now());
            session = null;
        }
    }
}
