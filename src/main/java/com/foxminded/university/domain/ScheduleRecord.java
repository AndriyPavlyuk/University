package com.foxminded.university.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduleRecord {
	private LocalDateTime time;
	private Subject subject;
	private Group group;
	private Room room;
	private long scheduleRecordID;
	
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
	
	public long getScheduleRecordID() {
		return scheduleRecordID;
	}

	public void setScheduleRecordID(long scheduleRecordID) {
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
