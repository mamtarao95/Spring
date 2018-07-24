package com.bridgelabz.fundoonoteapp.note.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "label")
public class Label {

	@Id
	private String labelId;
	
	private String userId;
	
	private String labelName;
	
	//private List<String> noteId;
/*
	public List<String> getNoteId() {
		return noteId;
	}

	public void setNoteId(List<String> noteId) {
		this.noteId = noteId;
	}*/

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
