package com.payq.reservation.dto;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Data;

import com.github.javafaker.Faker;


/**
 * @author shilpi chandra
 *
 * Booking Dto
 */
@Data
public class Booking {
	
	private Long id;
	private String firstname;
	private String lastname;
	private Double totalprice;
	private Boolean depositpaid;
	private BookingDates bookingdates;
	private String additionalneeds;
	Faker faker = new Faker();

	public Booking() {
		fillMockData();
	}

	public Booking(String firstname, String lastname, double totalprice, boolean depositpaid, BookingDates bookingdates,
			String additionalneeds) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.totalprice = totalprice;
		this.depositpaid = depositpaid;
		this.bookingdates = bookingdates == null ? new BookingDates(LocalDate.now().toString(), LocalDate.now().plusDays(7).toString()) : bookingdates;
		this.additionalneeds = additionalneeds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Booking booking = (Booking) o;
		return  depositpaid == booking.depositpaid
				&& Objects.equals(firstname, booking.firstname) && Objects.equals(lastname, booking.lastname)
				&& Objects.equals(bookingdates, booking.bookingdates)
				&& Objects.equals(additionalneeds, booking.additionalneeds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, lastname, totalprice, bookingdates, depositpaid, additionalneeds);
	}
	
	private void fillMockData() {
		this.firstname = faker.name().firstName();
		this.lastname = faker.name().firstName();
		this.totalprice = Math.random();
		this.bookingdates = new BookingDates(LocalDate.now().toString(), LocalDate.now().plusDays(7).toString());
		this.depositpaid = true;
		this.additionalneeds = faker.food().spice();
	}
}
