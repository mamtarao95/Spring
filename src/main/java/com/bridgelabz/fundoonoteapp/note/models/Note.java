package com.bridgelabz.fundoonoteapp.note.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "note")
public class Note {

	@Id
	private String noteId;
	private String userId;
	private String title;
	private String description;
	private Date createdAt;
	private Date updatedAt;
	private Date reminder;
	private boolean isTrashed;
	private String color;
	private boolean isPin;
	private boolean isArchive;
	private List<Label> labelList;

	public Note() {

	}

	public Note(String noteId, String userId, String title, String description, Date createdAt, Date updatedAt,
			Date reminder, boolean isTrashed, String color, boolean isPin, boolean isArchive, List<Label> labelList) {
		super();
		this.noteId = noteId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.reminder = reminder;
		this.isTrashed = isTrashed;
		this.color = color;
		this.isPin = isPin;
		this.isArchive = isArchive;
		this.labelList = labelList;
	}


	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	
	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

}
