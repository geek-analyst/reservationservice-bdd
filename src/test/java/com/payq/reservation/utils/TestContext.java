package com.payq.reservation.utils;

import com.payq.reservation.dto.Booking;

import io.restassured.response.Response;
import lombok.Data;

/**
 * @author shilpi chandra
 *
 * Context dto object to hold request and response details for a test.
 */
@Data
public class TestContext {
	private Response response;
	private Booking bookingRequest;
	private Booking bookingResponse;
	
}
