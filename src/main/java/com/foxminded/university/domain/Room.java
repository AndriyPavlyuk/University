package com.foxminded.university.domain;

import java.util.Objects;

public class Room {
	private Integer number;
	private Long roomID;
	
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