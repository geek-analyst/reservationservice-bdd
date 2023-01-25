package com.payq.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shilpi chandra
 * 
 * Booking Detail Dto
 */
@Data
@AllArgsConstructor
public class BookingDetails {
    private int bookingid;
    private Booking booking;

    public BookingDetails() {
        this.bookingid = 10;
        this.booking = new Booking();
    }

}
