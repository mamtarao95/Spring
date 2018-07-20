package com.bridgelabz.fundoonoteapp.note.models;

public class CreateNoteDTO {
	
	private static final long serialVersionUID = 1L;

	private String title;
	
	private String description;
	
	private String color;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public CreateNoteDTO() {
		super();
	}
	public CreateNoteDTO(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	
	}
	@Override
	public String toString() {
		return "CreateNoteDTO [title=" + title + ", description=" + description +  "]";
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
