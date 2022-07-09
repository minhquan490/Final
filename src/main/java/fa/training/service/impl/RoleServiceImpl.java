package fa.training.service.impl;

import fa.training.annotation.Inject;
import fa.training.annotation.resolver.InjectResolver;
import fa.training.entities.Role;
import fa.training.repository.RoleRepository;
import fa.training.service.RoleService;

import static fa.training.annotation.resolver.InjectType.REPOSITORY;

public class RoleServiceImpl implements RoleService {
    @Inject(REPOSITORY)
    private RoleRepository repository;

    @Override
    public boolean save(Role role) {
        InjectResolver.inject(this);
        return repository.save(role);
    }
}
