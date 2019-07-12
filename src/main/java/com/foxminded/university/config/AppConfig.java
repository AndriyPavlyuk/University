package com.foxminded.university.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
@ComponentScan("com.foxminded.university")
public class AppConfig {

	@Bean
	public DataSource dataSource() {
		return new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/postgres");
	}
}
