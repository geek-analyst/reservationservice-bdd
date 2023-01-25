package com.payq.reservation.services;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import com.payq.reservation.dto.Booking;
import com.payq.reservation.dto.BookingDetails;
import com.payq.reservation.utilities.Constants;
import com.payq.reservation.utilities.ReservationProperties;
import com.payq.reservation.utils.TestContext;
import com.payq.reservation.utils.Utils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

/**
 * @author shilpi chandra
 *
 * Booking Service Implementation
 */
public class BookingServiceImpl implements BookingService {

	private static String authToken;

	@Override
	public Response createBooking(TestContext context) throws JSONException {
		RequestSpecification request = Utils.getRequestSpec().basePath(Constants.BOOKING_ENDPOINT_PATH);
		Response response = sendRequest(request, Constants.POST_REQUEST, context.getBookingRequest());
		context.setResponse(response);

		return response;
	}

	@Override
	public String getBookingIdFromResponse(Response response) {
		BookingDetails bd = response.getBody().as(BookingDetails.class);
		return String.valueOf(bd.getBookingid());
	}

	@Override
	public Response deleteBookingById(String bookingId) throws RuntimeException {
		RequestSpecification rSpec = Utils.getRequestSpec().cookie(Constants.TOKEN, getAuthorizationToken())
				.basePath(getBasePath(bookingId));
		return sendRequest(rSpec, Constants.DELETE_REQUEST, null);
	}

	@Override
	public Response updateBooking(String bookingId, Booking booking) throws RuntimeException {
		RequestSpecification rSpec = Utils.getRequestSpec().cookie(Constants.TOKEN, getAuthorizationToken())
				.basePath(getBasePath(bookingId));
		return sendRequest(rSpec, Constants.PUT_REQUEST, booking);
	}

	public Response getBookingById(String bookingId, RequestSpecification rSpec) {
		rSpec = Utils.getRequestSpec().cookie(Constants.TOKEN, getAuthorizationToken())
				.basePath(getBasePath(bookingId));
		return sendRequest(rSpec, Constants.GET_REQUEST, null);
	}

	private String getBasePath(String bookingId) {
		return String.format("%s/%s", Constants.BOOKING_ENDPOINT_PATH, bookingId);
	}

	@Override
	public Response getBookingById(String bookingId) throws RuntimeException {

		Response response = RestAssured.get(ReservationProperties.getPropertyValue(Constants.APPLICATION_BASE_URL)
				+ Constants.APPLICATION_PATH_BOOKING + bookingId);

		return response;
	}

	@Override
	public Response getBookingByIdWithParam(String... params) throws RuntimeException {
		Response response = null;
		if (params != null && params.length == 1) {
			response = RestAssured.get(ReservationProperties.getPropertyValue(Constants.APPLICATION_BASE_URL)
					+ Constants.APPLICATION_PATH_BOOKING + "?firstname=" + params[0]);
		} else if (params != null && params.length == 2) {
			response = RestAssured.get(ReservationProperties.getPropertyValue(Constants.APPLICATION_BASE_URL)
					+ Constants.APPLICATION_PATH_BOOKING + "?firstname=" + params[0] + "&lastname=" + params[1]);
		}

		if (response.getStatusCode() != 200) {
			throw new RuntimeException(
					"Failed to retrieve booking with given parameters " + ". Status code: " + response.getStatusCode());
		}

		return response;
	}

	@Override
	public Response getAllBookingIDS() {
		Response response = RestAssured.get(ReservationProperties.getPropertyValue(Constants.APPLICATION_BASE_URL)
				+ Constants.APPLICATION_PATH_BOOKING);
		return response;
	}

	@Override
	public Response sendRequest(RequestSpecification request, int requestType, Object pojo) {
		Response response;

		// Add the Json to the body of the request
		if (pojo != null) {
			String payload = Utils.createJSONPayload(pojo).toString();
			request.body(payload);
		}

		// need to add a switch based on request type
		switch (requestType) {
		case Constants.POST_REQUEST:
			if (request == null) {
				response = SerenityRest.when().post();
			} else {
				response = request.post();
			}
			break;
		case Constants.DELETE_REQUEST:
			if (request == null) {
				response = SerenityRest.when().delete();
			} else {
				response = request.delete();
			}
			break;
		case Constants.PUT_REQUEST:
			if (request == null) {
				response = SerenityRest.when().put();
			} else {
				response = request.put();
			}
			break;
		case Constants.PATCH_REQUEST:
			if (request == null) {
				response = SerenityRest.when().patch();
			} else {
				response = request.patch();
			}
			break;
		case Constants.GET_REQUEST:
		default:
			if (request == null) {
				response = SerenityRest.when().get();
			} else {
				response = request.get();
			}
			break;
		}
		return response;
	}

	/**
	 * Returns authentication token by log in with the username and password
	 * provided in applicationUnderTest.properties Authentication token is required
	 * for update and delete requests
	 */
	public String getAuthorizationToken() {
		if (authToken == null || authToken.length() < 1) {
			JSONObject jsonObj = new JSONObject()
					.put(Constants.APPLICATION_USERNAME,
							ReservationProperties.getPropertyValue(Constants.APPLICATION_USERNAME))
					.put(Constants.APPLICATION_PASSWORD,
							ReservationProperties.getPropertyValue(Constants.APPLICATION_PASSWORD));
			RequestSpecification rspec = Utils.getRequestSpec().basePath(Constants.APPLICATION_PATH_AUTH)
					.body(jsonObj.toString());
			Response response = this.sendRequest(rspec, Constants.POST_REQUEST, null);
			authToken = response.jsonPath().getString(Constants.TOKEN);
		}

		return authToken;
	}

	@Override
	public void verifyBookingModel(TestContext context) {
		if (context.getResponse().contentType() != null
				&& context.getResponse().contentType().contains(ContentType.JSON.toString())
				|| context.getResponse().contentType().contains(ContentType.XML.toString())) {
			Assert.assertTrue(
					this.verifyBookingValuesAreAsExpected(context.getResponse(), context.getBookingRequest()));
		}
	}

	@Override
	public void createMultipleBooking(DataTable dt, TestContext context, List<Response> responseList) {
		List<List<String>> rows = dt.asLists(String.class);
		for (int i = 1; i < dt.cells().size(); i++) {
			List<String> columns = rows.get(i);
			Booking bo = new Booking(columns.get(0), columns.get(1), Integer.parseInt(columns.get(2)),
					Boolean.parseBoolean(columns.get(3)), null, columns.get(4));
			// TestContext context = new TestContext();
			context.setBookingRequest(bo);
			responseList.add(this.createBooking(context));
		}
	}

	@Override
	public void deleteBooking(TestContext context) {
		context.setBookingRequest(new Booking());
		// Create a booking to be deleted.
		Response response = this.createBooking(context);
		String bookingId = this.getBookingIdFromResponse(response);
		// Delete the booking created in above step.
		context.setResponse(this.deleteBookingById(bookingId));
	}

	@Override
	public void updateBooking(DataTable table, TestContext context) {
		context.setBookingRequest(new Booking());
		// Create a booking to be updated
		Response response = this.createBooking(context);
		String bookingId = this.getBookingIdFromResponse(response);
		Booking updatedBooking = Utils.getBooking(table);
		context.setBookingRequest(updatedBooking);
		// Update the booking created above
		context.setResponse(this.updatePartialBooking(bookingId, updatedBooking));
	}

	@Override
	public void getBookingById(TestContext context) {
		context.setBookingRequest(new Booking());
		Response tmpResp = this.createBooking(context);
		String bookingId = this.getBookingIdFromResponse(tmpResp);

		// Fetch the booking created above by it's Id.
		Response bookingResponse = this.getBookingById(bookingId);
		Booking booking = bookingResponse.as(Booking.class);
		context.setBookingResponse(booking);
	}

	@Override
	public void getBookingByParam(TestContext context) {
		Booking booking = Utils.getBooking();
		context.setBookingRequest(booking);
		this.createBooking(context);
		Response bookingsWithParam = this.getBookingByIdWithParam(booking.getFirstname(), booking.getLastname());
		context.setResponse(bookingsWithParam);
	}

	@Override
	public void verifyBookings(TestContext context) {
		BookingDetails[] bookings = context.getResponse().as(BookingDetails[].class);
		Assert.assertTrue(bookings.length > 0);
	}

	@Override
	public void verifyDataTable(DataTable dt, List<Response> responseList) {
		List<List<String>> rows = dt.asLists(String.class);
		for (int i = 1; i < dt.cells().size(); i++) {
			List<String> columns = rows.get(i);
			this.verifyResponseCode(responseList.get(i - 1), Integer.parseInt(columns.get(0)));
		}
	}

	@Override
	public void verifyUpdateBookingResponse(TestContext context) {
		if (context.getResponse().contentType() != null
				&& context.getResponse().contentType().contains(ContentType.JSON.toString())
				|| context.getResponse().contentType().contains(ContentType.XML.toString())) {
			Assert.assertTrue(this.verifyAfterUpdateBookingValuesAreAsExpected(context.getResponse(),
					context.getBookingRequest()));
		}
	}

	@Override
	public void verifyBookingAPIIsAvailable() {
		RequestSpecification rSpec = Utils.getRequestSpec().basePath(Constants.APPLICATION_PATH_PING);
		this.sendRequest(rSpec, Constants.GET_REQUEST, null).then().statusCode(Constants.HTTP_STATUS_CREATED);

	}

	@Override
	public boolean verifyAfterUpdateBookingValuesAreAsExpected(Response response, Booking booking)
			throws RuntimeException {
		if (response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to retrieve booking ids" + ". Status code: " + response.getStatusCode());
		}
		return booking.equals(response.getBody().as(Booking.class));
	}

	@Override
	public void verifyBookingIds(Response response) throws RuntimeException {
		if (response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to retrieve booking ids" + ". Status code: " + response.getStatusCode());
		}
		BookingDetails[] bookings = response.as(BookingDetails[].class);
		Assert.assertTrue(bookings.length > 0);
	}

	@Override
	public void verifyResponseCode(Response response, int expectedCode) {
		Assert.assertEquals(response.getStatusCode(), expectedCode);
	}

	@Override
	public void verifyListOfAllBookings(Response response) throws RuntimeException {
		if (response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to retrieve booking ids" + ". Status code: " + response.getStatusCode());
		}
		BookingDetails[] bookings = response.as(BookingDetails[].class);
		Assert.assertTrue(bookings.length > 0);
	}

	@Override
	public Response updatePartialBooking(String bookingId, Booking booking) throws RuntimeException {
		RequestSpecification rSpec = Utils.getRequestSpec().cookie(Constants.TOKEN, getAuthorizationToken())
				.basePath(getBasePath(bookingId));
		return sendRequest(rSpec, Constants.PATCH_REQUEST, booking);
	}

	@Override
	public boolean verifyBookingValuesAreAsExpected(Response response, Booking booking) throws RuntimeException {
		if (response.getStatusCode() != 200) {
			throw new RuntimeException(
					"Failed to retrieve booking with given parameters " + ". Status code: " + response.getStatusCode());
		}

		return booking.equals(response.getBody().as(BookingDetails.class).getBooking());
	}
}
