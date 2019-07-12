package com.foxminded.university.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "rooms")
@NamedQuery(name = Room.FIND_ALL, query = "FROM Room")
public class Room {
	public static final String FIND_ALL = "Room.findAll";
	@Id
	private Long roomID;
	@Column
	private Integer number;
	
	public Room() {

	}
	public Room(Integer number) {
		this.number = number;
	}

	public Room(Long roomID) {
		this.roomID = roomID;
	}
	
	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Room room = (Room) o;
		return Objects.equals(number, room.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
}