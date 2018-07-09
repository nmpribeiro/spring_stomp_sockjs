package spring_boot_app.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.Introspector;

/**
 * Global Access to the Spring BrokerApplication Context
 *
 * @implNote This is mainly needed to get Beans outside the application
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        ctx = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    // for debugging purposes
    public static void printServices() {
        System.out.println("----- SPRING BEAN DEFINITIONS -----");
        StringBuilder builder = new StringBuilder();
        for (String element : ctx.getBeanDefinitionNames()) {
            builder.append(element).append(System.lineSeparator());
        }
        System.out.println(builder.toString());
        System.out.println("-----------------------------------");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getUserService(Class<T> serviceClass) {
        return (T) ctx.getBean(Introspector.decapitalize(serviceClass.getSimpleName()));
    }
}

