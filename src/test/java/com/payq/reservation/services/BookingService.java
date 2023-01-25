package com.payq.reservation.services;

import java.util.List;

import org.json.JSONException;

import com.payq.reservation.dto.Booking;
import com.payq.reservation.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author shilpi chandra
 *
 * Booking Service
 */
public interface BookingService {

	/**
	 * Get booking response by Id.
	 * 
	 * @param bookingId
	 * @return {@link io.restassured.response.Response}
	 */
	public Response getBookingById(String bookingId);

	/**
	 * Create a booking and return the response.
	 * 
	 * @param bookingId
	 * @return {@link io.restassured.response.Response}
	 */
	public Response createBooking(TestContext context) throws JSONException;

	/**
	 * delete a booking by Id.
	 * 
	 * @param bookingId
	 * @return {@link io.restassured.response.Response}
	 */

	public Response deleteBookingById(String bookingId);

	/**
	 * Update a Booking
	 * 
	 * @param bookingId
	 * @param booking
	 * @return {@link io.restassured.response.Response}
	 * @throws RuntimeException
	 */
	public Response updateBooking(String bookingId, Booking booking) throws RuntimeException;

	/**
	 * Partial Update a Booking
	 * 
	 * @param bookingId  id of the booking to be updated 
	 * @param booking    input booking data
	 * @return {@link io.restassured.response.Response}
	 * @throws RuntimeException
	 */
	public Response updatePartialBooking(String bookingId, Booking booking) throws RuntimeException;

	/**
	 * Get Bookings by params e.g. firstname, lastname.
	 * 
	 * @param params
	 * @return {@link io.restassured.response.Response}
	 * @throws RuntimeException
	 */
	public Response getBookingByIdWithParam(String... params) throws RuntimeException;

	/**
	 * Get Authorization token
	 * 
	 * @return {String}
	 */
	public String getAuthorizationToken();

	/**
	 * Send request
	 * 
	 * @param request     details for sending the request
	 * @param requestType of the request. i.e GET, POST, PUT, DELETE, UPDATE
	 * @param url         to execute for the request
	 * @param pojo        if provided will be added to the body of request as JSON
	 *                    payload
	 * @return {@link io.restassured.response.Response} received from the service by sending the request
	 */
	public Response sendRequest(RequestSpecification request, int requestType, Object pojo);

	/**
	 * Verify that the response code is the same as expected code by comparing the
	 * provided expected code and the response code from the response received by
	 * sending the request
	 * 
	 * @param response
	 * @param expectedCode
	 */
	public void verifyResponseCode(Response response, int expectedCode);

	/**
	 * 
	 * Verify Booking list.
	 * 
	 * @param response
	 * @throws RuntimeException
	 */
	public void verifyListOfAllBookings(Response response) throws RuntimeException;

	/**
	 * Verify Booking Ids.
	 * 
	 * @param response
	 * @throws RuntimeException
	 */
	public void verifyBookingIds(Response response) throws RuntimeException;

	/**
	 * Verify updated booking.
	 * 
	 * @param {@link io.restassured.response.Response}
	 * @param booking
	 * @return {Boolean}
	 * @throws RuntimeException
	 */
	public boolean verifyAfterUpdateBookingValuesAreAsExpected(Response response, Booking booking)
			throws RuntimeException;

	/**
	 * Get all Booking ids.
	 * 
	 * @return {@link io.restassured.response.Response}
	 */
	public Response getAllBookingIDS();

	/**
	 * Verify Booking response.
	 * 
	 * @param response
	 * @param booking
	 * @return {Boolean}
	 * @throws RuntimeException
	 */
	public boolean verifyBookingValuesAreAsExpected(Response response, Booking booking) throws RuntimeException;

	/**
	 * Get Booking Id from {@link io.restassured.response.Response}.
	 * 
	 * @param response
	 * @return {String}
	 */
	public String getBookingIdFromResponse(Response response);

	/**
	 * Verify if Booking API is available
	 */
	public void verifyBookingAPIIsAvailable();
	
	/**
	 * Verify Booking Model
	 * 
	 * @param context
	 */
	public void verifyBookingModel(TestContext context);
	
	/**
	 * Verify response as per input data table
	 * 
	 * @param dt
	 * @param responseList
	 */
	public void verifyDataTable(DataTable dt, List<Response> responseList);
	
	/**
	 * Create multiple bookings based on input data table.
	 * @param dt
	 * @param context
	 * @param responseList
	 */
	public void createMultipleBooking(DataTable dt, TestContext context, List<Response> responseList);
	
	/**
	 * Delete Booking
	 * @param contex
	 * 
	 */
	public void deleteBooking(TestContext context);
	
	/**
	 * Update booking as per input data table
	 * 
	 * @param contex
	 * @param table
	 * 
	 */
	public void updateBooking(DataTable table, TestContext context);
	
	/**
	 * Get Booking By ID
	 * @param contex
	 */
	public void getBookingById(TestContext context);
	
	/**
	 * Get Booking by providing parameters
	 * 
	 * @param contex
	 */
	public void getBookingByParam(TestContext context);
	
	/**
	 * Verify Bookings
	 * @param context
	 */
	public void verifyBookings(TestContext context);
	
	/**
	 * Verify update booking response.
	 * @param context
	 */
	public void verifyUpdateBookingResponse(TestContext context);
}
