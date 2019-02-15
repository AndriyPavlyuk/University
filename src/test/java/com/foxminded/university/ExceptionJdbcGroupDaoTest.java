package com.foxminded.university;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcGroupDao;
import com.foxminded.university.domain.Group;

public class ExceptionJdbcGroupDaoTest {

	private JdbcGroupDao jdbcGroupDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws DaoException {
		jdbcGroupDao = new JdbcGroupDao();
		thrown.expect(DaoException.class);
	}

	@Test
	public void testFindById() throws DaoException {
		thrown.expectMessage(is("Can not find group"));
		jdbcGroupDao.findById(-2);
	}

	@Test
	public void testAddGroupWithSameID() throws DaoException {
		Group group = new Group();
		Group groupSameID = new Group();
		long groupID = 3;
		String name = "SR-03";
		group.setGroupID(groupID);
		group.setName(name);
		groupSameID.setGroupID(groupID);
		groupSameID.setName(name);
		thrown.expectMessage(is("Can not add group"));	
		jdbcGroupDao.addGroup(group);
		jdbcGroupDao.addGroup(groupSameID);
	}

	@Test
	public void testRemoveGroup() throws DaoException {
		long groupID = -2;
		String name = "SR-02";
		Group group = new Group();
		group.setGroupID(groupID);
		group.setName(name);
		thrown.expectMessage(is("Can not remove group"));
		jdbcGroupDao.removeGroup(group);		
	}

	@Test
	public void testUpdateGroup() throws DaoException {
		/* Given */
		long groupID = -5;
		String name = "SR-03";
		Group group = new Group();
		group.setGroupID(groupID);
		group.setName(name);
		thrown.expectMessage(is("Can not update group"));	
		jdbcGroupDao.updateGroup(group);
	}
}
