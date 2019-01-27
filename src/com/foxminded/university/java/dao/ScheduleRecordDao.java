package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.foxminded.university.java.domain.ScheduleRecord;

public interface ScheduleRecordDao {
	
	Collection<ScheduleRecord> findAll() throws SQLException;
    
	ScheduleRecord findById(long id) throws SQLException;
     
     void addScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException;
    
     void updateScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException;
     
     void removeScheduleRecord(ScheduleRecord scheduleRecord) throws SQLException;
}
