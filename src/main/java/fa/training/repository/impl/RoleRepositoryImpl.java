package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.entities.Role;
import fa.training.repository.RoleRepository;
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
public class RoleRepositoryImpl implements RoleRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Role role) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.save(role);
            log.info("Persist " + role.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean edit(Role role) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.update(role);
            log.info("Update " + role.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public boolean delete(Role role) {
        inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (transaction != null) {
                log.info("Begin transaction at: " + LocalDateTime.now());
            }
            session.delete(role);
            log.info("Delete " + role.getClass().getSimpleName() + " success at: " + LocalDateTime.now());
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
    public Role get(int roleId) {
        String hql = "select r from Role r where r.roleId = :roleId";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("roleId", roleId);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Role) query.getSingleResult();
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
    public Role get(String roleName) {
        String hql = "select r from Role r where r.roleName = :roleName";
        inject(this);
        try {
            Query query = session.createQuery(hql).setParameter("roleName", roleName);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Role) query.getSingleResult();
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
    public Set<Role> getAll() {
        String hql = "select r from Role r";
        inject(this);
        try {
            Query query = session.createQuery(hql);
            log.info("Query data base success and retrieve data at: " + LocalDateTime.now());
            return (Set<Role>) query.getResultStream().collect(Collectors.toSet());
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
