package fa.training.config;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Properties;
import java.util.Set;

@Log4j2
public class HibernateConfig {

    public static final String URL = "jdbc:sqlserver://" + HibernateConfig.DB.SERVER_ADDRESS.getValue() + ":"
            + HibernateConfig.DB.SERVER_PORT.getValue() + ";" + "database=" + HibernateConfig.DB.DB_NAME.getValue()
            + ";" + "trustServerCertificate=true;";
    private static SessionFactory sessionFactory;

    private HibernateConfig() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration hibernateConfiguration = new Configuration();

                Properties hibernateProperties = new Properties();
                hibernateProperties.put(Environment.DRIVER, HibernateConfig.DB.DB_DRIVER.getValue());
                hibernateProperties.put(Environment.URL, URL);
                hibernateProperties.put(Environment.USER, HibernateConfig.DB.USERNAME.getValue());
                hibernateProperties.put(Environment.PASS, HibernateConfig.DB.PASSWORD.getValue());
                hibernateProperties.put(Environment.DIALECT, HibernateConfig.DB.DIALECT.getValue());
                hibernateProperties.put(Environment.AUTOCOMMIT, "false");
                hibernateProperties.put(Environment.SHOW_SQL, "fail");
                hibernateProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                hibernateProperties.put(Environment.FORMAT_SQL, "true");
                hibernateProperties.put(Environment.HBM2DDL_AUTO, "update");
                hibernateProperties.put(Environment.STATEMENT_BATCH_SIZE, "100");
                hibernateProperties.put(Environment.ORDER_INSERTS, "true");
                hibernateProperties.put(Environment.ORDER_UPDATES, "true");
                hibernateProperties.put(Environment.BATCH_VERSIONED_DATA, "true");
                hibernateProperties.put(Environment.C3P0_MIN_SIZE, "5");
                hibernateProperties.put(Environment.C3P0_MAX_SIZE, "20");
                hibernateProperties.put(Environment.C3P0_TIMEOUT, "300");
                hibernateProperties.put(Environment.C3P0_MAX_STATEMENTS, "50");
                hibernateProperties.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");

                hibernateConfiguration.setProperties(hibernateProperties);

                Set<Class<?>> classes = new Reflections(HibernateConfig.DB.BASE_ENTITIES_PACKAGE.getValue())
                        .getTypesAnnotatedWith(Entity.class);
                if (!classes.isEmpty()) {
                    log.debug("Scan entities package success");
                }
                for (Class<?> a : classes) {
                    log.debug("Add " + a.getSimpleName() + " to Metadata");
                    hibernateConfiguration.addAnnotatedClass(a);
                }

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(hibernateConfiguration.getProperties()).build();

                sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry);
                if (sessionFactory != null) {
                    log.debug("Create Session Factory success");
                }
            } catch (HibernateException e) {
                log.debug("Has problem because: " + e.getMessage());
                log.error(e.getMessage(), e);
            }
        }
        return sessionFactory;
    }

    private enum DB {
        DB_DRIVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"), SERVER_ADDRESS("localhost"),
        DB_NAME("Final-hibernate"), SERVER_PORT("1433"), USERNAME("sa"), PASSWORD("Quan3008"),
        DIALECT("org.hibernate.dialect.SQLServerDialect"), BASE_ENTITIES_PACKAGE("fa.training.entities");

        private final String value;

        DB(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
