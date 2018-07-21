package com.bridgelabz.fundoonoteapp.note.models;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="note")
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    

	
	


}
	
	