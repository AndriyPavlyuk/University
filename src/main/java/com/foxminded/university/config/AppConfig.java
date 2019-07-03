package com.foxminded.university.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.foxminded.university.dao.DataSourceConnection;
import com.foxminded.university.dao.JdbcGroupDao;
import com.foxminded.university.dao.JdbcStudentDao;

@Configuration
@ComponentScan("com.foxminded.university")
public class AppConfig {

	@Bean
	public DataSource dataSource() {
		return new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/postgres");
	}

	@Bean
	public DataSourceConnection dataSourceConnection() {
		return new DataSourceConnection(dataSource());
	}
}
