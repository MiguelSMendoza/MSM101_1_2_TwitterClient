package es.netrunners.twitterclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweet implements Comparable<Tweet> {

	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss Z");

	private String username;
	private String imageSrc;
	private String message;
	private Date date;

	public Tweet(String u, String i, String m, Date d) {
		this.username = u;
		this.imageSrc = i;
		this.message = m;
		this.date = d;
	}

	public Tweet() {
		this.username = "";
		this.imageSrc = "";
		this.message = "";
		this.date = new Date();
	}

	@Override
	public int compareTo(Tweet another) {
		if (another == null)
			return 1;
		return another.date.compareTo(date);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the imageSrc
	 */
	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * @param imageSrc
	 *            the imageSrc to set
	 */
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return FORMATTER.format(this.date);
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		while (!date.endsWith("00")) {
			date += "0";
		}
		try {
			this.date = FORMATTER.parse(date.trim());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Tweet copy() {
		return new Tweet(this.username, this.imageSrc, this.message, this.date);

	}

}
