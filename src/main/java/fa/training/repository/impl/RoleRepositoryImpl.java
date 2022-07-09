package fa.training.repository.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.resolver.InjectResolver;
import fa.training.entities.Role;
import fa.training.repository.RoleRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

import static fa.training.annotation.resolver.InjectType.SESSION;

public class RoleRepositoryImpl implements RoleRepository {

    @Inject(SESSION)
    private Session session;

    @Override
    public boolean save(Role role) {
        InjectResolver.inject(this);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {transaction.rollback();}
            return false;
        } finally {
            if (session != null) {session.close();}
        }
    }

    @Override
    public boolean edit(Role role) {
        return false;
    }

    @Override
    public boolean delete(Role role) {
        return false;
    }

    @Override
    public Role get(int roleId) {
        return null;
    }

    @Override
    public Role get(String roleName) {
        return null;
    }

    @Override
    public Set<Role> getAll() {
        return null;
    }
}
