package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.entities.Apartment;
import fa.training.repository.ApartmentRepository;
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
public class ApartmentRepositoryImpl implements ApartmentRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Apartment apartment) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.save(apartment);
            log.info("Persist " + apartment.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean edit(Apartment apartment) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.update(apartment);
            log.info("Update " + apartment.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean delete(Apartment apartment) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.delete(apartment);
            log.info("Delete " + apartment.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public Apartment get(int apartmentId) {
        String hql = "select a from Apartment a where a.apartmentId = :apartmentId";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("apartmentId", apartmentId);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Apartment) query.getSingleResult();
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
    public Apartment get(String apartmentCode) {
        String hql = "select a from Apartment a where a.apartmentCode = :apartmentCode";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("apartmentCode", apartmentCode);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Apartment) query.getSingleResult();
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
    public Set<Apartment> get(int numBedroom, String doorDirection, String status) {
        String hql = "select a from Apartment a where a.numBedroom = :numBedroom and a.doorDirection = :doorDirection and a.status = :status";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("numBedroom", numBedroom).setParameter("doorDirection", doorDirection).setParameter("status", status);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Apartment>) query.getResultStream().collect(Collectors.toSet());
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
    public Set<Apartment> getByStatus(String status) {
        String hql = "select a from Apartment a where a.status = :status";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("status", status);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Apartment>) query.getResultStream().collect(Collectors.toSet());
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
    public Set<Apartment> getAll() {
        String hql = "select a from Apartment a";
        inject(this);
        try {
            Query query = session.createQuery(hql);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Apartment>) query.getResultStream().collect(Collectors.toSet());
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
