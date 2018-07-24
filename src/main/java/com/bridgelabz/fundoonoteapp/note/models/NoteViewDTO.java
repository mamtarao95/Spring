package com.bridgelabz.fundoonoteapp.note.models;

import java.util.Date;

public class NoteViewDTO {
	private String noteId;
	private String title;
	private String description;
	private Date createdAt;
	private Date updatedAt;
	private Date reminder;
	private String color;

	public NoteViewDTO() {
		
	}
	public NoteViewDTO(String noteId, String title, String description, Date createdAt, Date updatedAt, Date reminder,
			String color) {
		super();
		this.noteId = noteId;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.reminder = reminder;
		this.color = color;
	}

	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

}
