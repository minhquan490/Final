package fa.training.annotation.resolver;

import fa.training.annotation.Inject;
import fa.training.annotation.Repository;
import fa.training.annotation.Service;
import fa.training.config.HibernateConfig;
import fa.training.exception.ServerInternalException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class InjectResolver {

    private static final SessionFactory factory = HibernateConfig.getSessionFactory();
    private static final ConcurrentHashMap<String, String> repositoryImpl = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> serviceImpl = new ConcurrentHashMap<>();

    public static void inject(Object instance) {
        if (repositoryImpl.isEmpty()) {
            Set<Class<?>> classes = new Reflections("fa.training.repository").getTypesAnnotatedWith(Repository.class);
            classes.forEach(c -> {
                repositoryImpl.put(c.getSimpleName().substring(0, c.getSimpleName().length() - 4), c.getName());
            });
        }
        if (serviceImpl.isEmpty()) {
            Set<Class<?>> classes = new Reflections("fa.training.service").getTypesAnnotatedWith(Service.class);
            classes.forEach(c -> {
                serviceImpl.put(c.getSimpleName().substring(0, c.getSimpleName().length() - 4), c.getName());
            });
        }
        try {
            Field[] fields = instance.getClass().getDeclaredFields();//get field is annotated
            for (Field f : fields) {
                Inject inject = f.getAnnotation(Inject.class);
                //check if field is mark by @Inject if true inject it via option value
                if (f.isAnnotationPresent(Inject.class) && (inject.value().equals(InjectType.REPOSITORY))) {
                    f.setAccessible(true);
                    f.set(instance, Class.forName(repositoryImpl.get(f.getType().getSimpleName())).newInstance());
                }
                if (f.isAnnotationPresent(Inject.class) && (inject.value().equals(InjectType.SERVICE))) {
                    f.setAccessible(true);
                    f.set(instance, Class.forName(serviceImpl.get(f.getType().getSimpleName())).newInstance());
                }
                if (f.isAnnotationPresent(Inject.class) && inject.value().equals(InjectType.SESSION)) {
                    f.setAccessible(true);
                    f.set(instance, factory.openSession());
                    log.info("Open session at: " + LocalDateTime.now());
                }
                if (f.isAnnotationPresent(Inject.class) && inject.value().equals(InjectType.ALL)) {
                    f.setAccessible(true);
                    f.set(instance, Class.forName(f.getType().getName()).newInstance());
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new ServerInternalException("Some thing wrong, please try again later");
        }
    }
}
