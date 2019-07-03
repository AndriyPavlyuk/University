package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJdbcDao {
	private static final Logger logger = LoggerFactory.getLogger(AbstractJdbcDao.class);
	public Connection getConnection() throws DaoException {
		try {
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/postgres");
            if (ds == null) {
                logger.error("No DataSource");
                throw new DaoException("Data source not found!");
            }
            return ds.getConnection();
        } catch (SQLException | NamingException e) {
            logger.error("Exception during receiving DataSource", e.getCause());
            throw new DaoException("Can not receive DataSource",e);
        }
	}
}