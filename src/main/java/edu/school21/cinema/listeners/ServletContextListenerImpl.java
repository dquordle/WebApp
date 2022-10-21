package edu.school21.cinema.listeners;

import edu.school21.cinema.config.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("springContext", new AnnotationConfigApplicationContext(ApplicationConfig.class));
    }
}
