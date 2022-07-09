package fa.training.repository;

import fa.training.entities.Role;

import java.util.Set;

public interface RoleRepository {
    boolean save(Role role);
    boolean edit(Role role);
    boolean delete(Role role);
    Role get(int roleId);
    Role get(String roleName);
    Set<Role> getAll();
}
