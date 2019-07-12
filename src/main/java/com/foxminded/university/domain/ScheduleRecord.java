package com.foxminded.university.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "scheduleRecord")
@NamedQuery(name = ScheduleRecord.FIND_ALL, query = "FROM ScheduleRecord")
public class ScheduleRecord {
	public static final String FIND_ALL = "Group.findAll";
	@Id
	private Long scheduleRecordID;
	@Column
	private LocalDateTime time;
	@ManyToOne
    @JoinColumn(name = "subject_id")
	private Subject subject;
	@ManyToOne
    @JoinColumn(name = "group_id")
	private Group group;
	@ManyToOne
    @JoinColumn(name = "room_id")
	private Room room;
	
	public ScheduleRecord() {

	}

	public ScheduleRecord(LocalDateTime time, Room room) {
		this.time = time;
		this.room = room;
	}

	public ScheduleRecord(LocalDateTime time, Subject subject, Room room) {
		this.time = time;
		this.subject = subject;
		this.room = room;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public Long getScheduleRecordID() {
		return scheduleRecordID;
	}

	public void setScheduleRecordID(Long scheduleRecordID) {
		this.scheduleRecordID = scheduleRecordID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ScheduleRecord that = (ScheduleRecord) o;
		return time.isEqual(that.time) && Objects.equals(room, that.room);
	}

	@Override
	public int hashCode() {
		return Objects.hash(time, room);
	}
}
