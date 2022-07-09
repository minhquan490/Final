package fa.training;

import fa.training.annotation.Inject;
import fa.training.annotation.resolver.InjectResolver;
import fa.training.entities.Role;
import fa.training.service.RoleService;

import static fa.training.annotation.resolver.InjectType.SERVICE;

public class Application {

    @Inject(SERVICE)
    private RoleService service;

    public void run() {
        InjectResolver.inject(this);
        Role role = Role.createRole("test");
        service.save(role);
    }
}
