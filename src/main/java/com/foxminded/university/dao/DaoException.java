package com.foxminded.university.dao;

@SuppressWarnings("serial")
public class DaoException extends Exception {
     
    public DaoException() {
    	super();
    }
 
    public DaoException(String message) {
        super(message);
    }
 
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
