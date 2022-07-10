package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.entities.Customer;
import fa.training.repository.CustomerRepository;
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

@Repository
@Log4j2
public class CustomerRepositoryImpl implements CustomerRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Customer customer) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.save(customer);
            log.info("Persist " + customer.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean edit(Customer customer) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.update(customer);
            log.info("Update " + customer.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean delete(Customer customer) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.delete(customer);
            log.info("Delete " + customer.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public Customer get(int customerId) {
        String hql = "select c from Customer c join fetch c.bills where c.customerId = :customerId";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("customerId", customerId);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Customer) query.getSingleResult();
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
    public Customer get(String phone) {
        String hql = "select c from Customer c join fetch c.bills where c.phone = :phone";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("phone", phone);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Customer) query.getSingleResult();
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
    public Customer getIfBillNull(String phone) {
        String hql = "select c from Customer c where c.phone = :phone";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("phone", phone);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Customer) query.getSingleResult();
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
    public Set<Customer> getALl() {
        String hql = "select c from Customer c join fetch c.bills";
        inject(this);
        try {
            Query query = session.createQuery(hql);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Customer>) query.getResultStream().collect(Collectors.toSet());
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
