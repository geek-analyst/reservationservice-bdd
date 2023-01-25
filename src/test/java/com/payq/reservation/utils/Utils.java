package com.payq.reservation.utils;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.payq.reservation.dto.Booking;
import com.payq.reservation.dto.BookingDates;
import com.payq.reservation.utilities.Constants;
import com.payq.reservation.utilities.ReservationProperties;

import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

/**
 * 
 * @author Shilpi Chandra
 * 
 * Utility methods
 *
 */
public class Utils {

	public static Booking getBooking(DataTable table) {
		List<Map<String, String>> data = table.asMaps(String.class, String.class);
		String firstName = data.get(0).get(Constants.FIRST_NAME);
		String lastName = data.get(0).get(Constants.LAST_NAME);
		String totalPrice = data.get(0).get(Constants.TOTAL_PRICE);
		String depositPaid = data.get(0).get(Constants.DEPOSIT_PAID);
		String additionalNeeds = data.get(0).get(Constants.ADD_NEEDS);

		Booking updatedBooking = new Booking(firstName, lastName, Integer.parseInt(totalPrice),
				Boolean.parseBoolean(depositPaid), null, additionalNeeds);
		return updatedBooking;
	}
	
	/**
	 * Convert POJO to JSON
	 */
	public static JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}

	/**
	 * common specification for request
	 */
	public static RequestSpecification getRequestSpec() {
		RequestSpecification rSpec = SerenityRest.given();
		rSpec.contentType(ContentType.JSON)
				.baseUri(ReservationProperties.getPropertyValue(Constants.APPLICATION_BASE_URL));
		return rSpec;
	}
	
	/**
	 * Get a dummy Booking stub
	 */
	public static Booking getBooking() {
		
		Faker faker = new Faker();
		Name name = faker.name();
		String firstName = name.firstName();
		String lastName = name.lastName();
		Booking booking = new Booking(firstName, lastName, 11.11, true,
				new BookingDates(Instant.now().toString(), Instant.now().toString()), "test");
	    return 	booking;
		
	}
	
}
