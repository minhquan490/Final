package fa.training.annotation.resolver;

import fa.training.annotation.Inject;
import fa.training.config.HibernateConfig;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class InjectResolver {

    private static final SessionFactory factory = HibernateConfig.getSessionFactory();

    public static void inject(Object instance) {
        try{
            Field[] fields = instance.getClass().getDeclaredFields();//get field is annotated
            for (Field f : fields) {
                Inject inject = f.getAnnotation(Inject.class);
                //check if field is mark by @Inject if true inject it via option value
                if (f.isAnnotationPresent(Inject.class) && (inject.value().equals(InjectType.REPOSITORY) || inject.value().equals(InjectType.SERVICE))) {
                    f.setAccessible(true);
                    f.set(instance, Class.forName(new StringBuffer(f.getType().getPackage() + ".impl." + f.getType().getSimpleName() + "Impl").substring(7).trim()).newInstance());
                }
                if (f.isAnnotationPresent(Inject.class) && inject.value().equals(InjectType.SESSION)) {
                    f.setAccessible(true);
                    f.set(instance, factory.openSession());
                }
                if (f.isAnnotationPresent(Inject.class) && inject.value().equals(InjectType.ALL)) {
                    f.setAccessible(true);
                    f.set(instance, Class.forName(new StringBuffer(f.getType().getPackage() + "." + f.getType().getSimpleName()).substring(7).trim()).newInstance());
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
        }
    }
}
