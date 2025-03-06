package com.webapp.lms.model;
import jakarta.persistence.*;
@Entity
@Table(name = "book")

public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String title;
	private String author;
	private String isbn;
	private int publishedYear;
	public Book() {}
	
	public Book(Long id,String title,String author,String isbn,int publishedYear) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.publishedYear = publishedYear;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public int getPublishedYear() {
		return publishedYear;
	}
	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}
	
	public String toString() {
		return "id :" + id + "\n" +
				"title : " + title + "\n" +  
				"author : " + author + "\n" +
				"publishedYear : " + publishedYear;
	}
	
	
}