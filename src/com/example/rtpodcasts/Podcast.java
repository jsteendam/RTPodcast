package com.example.rtpodcasts;

public class Podcast {
	private String title = "";
	private String weblink = "";
	private String author = "";
	private String date = "";
	private String description = "";
	private String download_url = "";
	private String item_nr = "";
	private String duration = "";

	public Podcast() {

	}

	public Podcast(String title, String weblink, String author, String date, String description, String download_url, String item_nr, String duration) {
		this.title = title;
		this.weblink = weblink;
		this.author = author;
		this.date = date;
		this.description = description;
		this.download_url = download_url;
		this.item_nr = item_nr;
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}

	public String getItem_nr() {
		return item_nr;
	}

	public void setItem_nr(String item_nr) {
		this.item_nr = item_nr;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Podcast [title=" + title + ", weblink=" + weblink + ", author=" + author + ", date=" + date + ", description=" + description + ", download_url=" + download_url + ", item_nr=" + item_nr + ", duration=" + duration + "]";
	}
}
