package com.anoush.t1bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
public class T1BillBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(T1BillBackApplication.class, args);
	}
}
