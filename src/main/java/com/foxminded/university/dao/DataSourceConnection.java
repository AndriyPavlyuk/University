package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConnection {

	public DataSource dataSource;

	public DataSourceConnection() {
	}

	@Autowired
	public DataSourceConnection(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConnection.class);

	public Connection getDataSourceConnection() throws DaoException {
		try {
			if (dataSource == null) {
				logger.error("No DataSource");
				throw new DaoException("Data source not found!");
			}
			return dataSource.getConnection();
		} catch (SQLException | DaoException e) {
			logger.error("Exception during receiving dataSource", e.getCause());
			throw new DaoException();
		}
	}
}