package com.bridgelabz.fundoonoteapp.note.models;

import java.util.Date;
import java.util.List;

public class NoteDTO {
	private String noteId; // make it id
	private String title;
	private String description;
	private Date createdAt;
	private Date updatedAt;
	private Date reminder;
	private String color;
	private boolean isArchive;
	private boolean isPin;
	private List<LabelDTO> labels;
	
	public NoteDTO() {
		
	}
	
	public NoteDTO(String noteId, String title, String description, Date createdAt, Date updatedAt, Date reminder,
			String color, boolean isArchive, boolean isPin, List<LabelDTO> labels) {
		super();
		this.noteId = noteId;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.reminder = reminder;
		this.color = color;
		this.isArchive = isArchive;
		this.isPin = isPin;
		this.labels = labels;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
	public boolean isPin() {
		return isPin;
	}
	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}
	public List<LabelDTO> getLabels() {
		return labels;
	}
	public void setLabels(List<LabelDTO> labels) {
		this.labels = labels;
	}


}
