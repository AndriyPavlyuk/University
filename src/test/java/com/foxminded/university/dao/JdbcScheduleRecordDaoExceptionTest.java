package com.foxminded.university.dao;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.JdbcScheduleRecordDao;
import com.foxminded.university.dao.RoomDao;
import com.foxminded.university.dao.SubjectDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Room;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Subject;

public class JdbcScheduleRecordDaoExceptionTest {
	private JdbcScheduleRecordDao jdbcScheduleRecordDao;
	private GroupDao groupDao;
	private SubjectDao subjectDao;
	private RoomDao roomDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		groupDao = Mockito.mock(GroupDao.class);
		subjectDao = Mockito.mock(SubjectDao.class);
		roomDao=Mockito.mock(RoomDao.class);
		jdbcScheduleRecordDao = new JdbcScheduleRecordDao(groupDao, subjectDao, roomDao);
		Mockito.when(groupDao.findById(groupID)).thenReturn(new Group(groupID));
		Mockito.when(subjectDao.findById(subjectID)).thenReturn(new Subject(subjectID));
		Mockito.when(roomDao.findById(roomID)).thenReturn(new Room(roomID));
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindByIdNegativeValue() throws DaoException {
		thrown.expectMessage(is("Can not find schedule record"));
		jdbcScheduleRecordDao.findById(-1);
	}

	@Test
	public void testFindByIdSubjectNotExist() throws DaoException {
		thrown.expectMessage(is("Can not find schedule record"));
		jdbcScheduleRecordDao.findById(15);
	}
	
	@Test
	public void testAddSubjectWithSameID() throws DaoException {
		long scheduleRecordID=3;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		ScheduleRecord scheduleRecordSameID = new ScheduleRecord();
		scheduleRecordSameID.setScheduleRecordID(scheduleRecordID);
		scheduleRecordSameID.setTime(time);
		scheduleRecordSameID.setGroup(groupDao.findById(groupID));
		scheduleRecordSameID.setSubject(subjectDao.findById(subjectID));
		scheduleRecordSameID.setRoom(roomDao.findById(roomID));
		thrown.expectMessage(is("Can not add schedule record"));	
		jdbcScheduleRecordDao.addScheduleRecord(scheduleRecord);
		jdbcScheduleRecordDao.addScheduleRecord(scheduleRecordSameID);
	}

	@Test
	public void testRemoveGroup() throws DaoException {
		long scheduleRecordID=-2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=3;
		long subjectID=2;
		long roomID=1;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		thrown.expectMessage(is("Can not remove schedule record"));
		jdbcScheduleRecordDao.removeScheduleRecord(scheduleRecord);		
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long scheduleRecordID=-2;
		LocalDateTime time=LocalDateTime.of(2010,01,01,12,00,00);
		long groupID=2;
		long subjectID=3;
		long roomID=2;
		ScheduleRecord scheduleRecord = new ScheduleRecord();
		scheduleRecord.setScheduleRecordID(scheduleRecordID);
		scheduleRecord.setTime(time);
		scheduleRecord.setGroup(groupDao.findById(groupID));
		scheduleRecord.setSubject(subjectDao.findById(subjectID));
		scheduleRecord.setRoom(roomDao.findById(roomID));
		thrown.expectMessage(is("Can not update schedule record"));
		jdbcScheduleRecordDao.updateScheduleRecord(scheduleRecord);;
	}
}
