package com.foxminded.university.dao;

import java.util.Collection;

import com.foxminded.university.domain.ScheduleRecord;

public interface ScheduleRecordDao {
	
	Collection<ScheduleRecord> findAll() throws DaoException;
    
	ScheduleRecord findById(long id) throws DaoException;
     
     void addScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException;
    
     void updateScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException;
     
     void removeScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException;
}
