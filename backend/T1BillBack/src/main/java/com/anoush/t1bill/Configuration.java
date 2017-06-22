package com.anoush.t1bill;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.hibernate.jpa.HibernateEntityManagerFactory;

@Component
public class Configuration {

        @Bean
        public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
                return hemf.getSessionFactory();
        }

}
