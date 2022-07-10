package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.entities.Bill;
import fa.training.repository.BillRepository;
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
public class BillRepositoryImpl implements BillRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Bill bill) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.save(bill);
            log.info("Persist " + bill.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean edit(Bill bill) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.update(bill);
            log.info("Update " + bill.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean delete(Bill bill) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.delete(bill);
            log.info("Delete " + bill.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    @SuppressWarnings("unchecked")
    public Set<Bill> getAll() {
        String hql = "select b from Bill b";
        inject(this);
        try {
            Query query = session.createQuery(hql);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Bill>) query.getResultStream().collect(Collectors.toSet());
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
