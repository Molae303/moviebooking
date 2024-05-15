package wk.model;

import java.util.Date;

public class BookingVO {
	
	private int bookingId;
	private String userId;
	private int movieCd;
	private Date bookingDate;
	private int price;
	
	public BookingVO() {
		super();
	}

	public BookingVO(int bookingId, String userId, int movieCd, Date bookingDate, int price) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.movieCd = movieCd;
		this.bookingDate = bookingDate;
		this.price = price;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMovieCd() {
		return movieCd;
	}

	public void setMovieCd(int movieCd) {
		this.movieCd = movieCd;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
