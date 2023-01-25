package com.payq.reservation.stepdefs;

import java.util.ArrayList;
import java.util.List;

import com.payq.reservation.dto.Booking;
import com.payq.reservation.services.BookingService;
import com.payq.reservation.services.BookingServiceImpl;
import com.payq.reservation.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

/**
 * @author shilpi chandra
 *
 * Step definitions
 */
public class StepsDefinition {
	TestContext context = new TestContext();
	String bookingId;
	List<Response> responseList = new ArrayList<>();
	BookingService bookingService = new BookingServiceImpl();

	@Given("Booking API is active")
	public void booking_API_is_available() {
		bookingService.verifyBookingAPIIsAvailable();
	}

	@When("I POST a create booking")
	public void createBooking() {
		context.setBookingRequest(new Booking());
		bookingService.createBooking(context);
	}

	@Then("I see response has {int} status code")
	public void verifyResponseCode(int code) {
		bookingService.verifyResponseCode(context.getResponse(), code);
	}

	@Then("I verify booking request response as per booking model")
	public void verifyBookingResponseModel() {
		bookingService.verifyBookingModel(context);
	}

	@Then("the response has following response code")
	public void verifyDataTable(DataTable dt) {
		bookingService.verifyDataTable(dt, responseList);
	}

	@When("I POST a create booking API for multiple users")
	public void createMultipleBookings(DataTable dt) {
		bookingService.createMultipleBooking(dt, context, responseList);
	}

	@When("I delete a booking by id")
	public void deleteBooking() {
		bookingService.deleteBooking(context);
	}

	@When("getting the same booking with Id")
	public void gettingTheSameBookingWithId() {
		Response response = bookingService.getBookingById(bookingId);
		context.setResponse(response);
	}

	@When("^I update the booking with$")
	public void updateBooking(DataTable table) {
		bookingService.updateBooking(table, context);
	}

	@When("^I retrieve valid booking with ID$")
	public void getBookingById() {
		bookingService.getBookingById(context);
	}

	@Then("^I verify booking request response as per booking details model$")
	public void verifyBookingDetails() {
		Response allBookingResponse = bookingService.getAllBookingIDS();
		bookingService.verifyListOfAllBookings(allBookingResponse);
	}

	@When("^I retrieve all the booking Ids$")
	public void getBookingIds() {
		context.setResponse(bookingService.getAllBookingIDS());
	}

	@Then("^I verify booking details response has booking ids$")
	public void verifyBookingIds() {
		Response allBookingResponse = context.getResponse();
		bookingService.verifyBookingIds(allBookingResponse);
	}

	@When("^I retrieve valid booking with ID and Param$")
	public void getBookingByParam() {
		bookingService.getBookingByParam(context);
	}

	@Then("^I verify bookings$")
	public void verifyBookings() {
		bookingService.verifyBookings(context);
	}

	@Then("^I verify booking update response$")
	public void verifyUpdateBookingResponse() {
		bookingService.verifyUpdateBookingResponse(context);
	}

}
