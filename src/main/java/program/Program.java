package program;

import com.sun.javafx.runtime.SystemProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Property;
import java.util.Properties;

/**
 * Created by aldm on 16.02.2016.
 */
public class Program {
    private static final Logger logger = LogManager.getLogger(Program.class);
    public static void main(String[] args) {
        /*SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();*/
        logger.info("I say");
    }
}
