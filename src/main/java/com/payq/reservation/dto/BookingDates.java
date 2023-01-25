package com.payq.reservation.dto;

import java.util.Objects;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author shilpi chandra
 * 
 * BookingDates Dto
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDates {
	private String checkin;
	private String checkout;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		BookingDates that = (BookingDates) obj;
		return Objects.equals(checkin, that.checkin) && Objects.equals(checkout, that.checkout);
	}

	@Override
	public int hashCode() {
		return Objects.hash(checkin, checkout);
	}

}
