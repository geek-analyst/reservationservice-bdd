package com.payq.reservation.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shilpi chandra
 * 
 * Holds the properties of the application.
 */

public class ReservationProperties {

	private static final Logger logger = LoggerFactory.getLogger(ReservationProperties.class);
	private static final Properties PROPERTIES = new Properties();

	@Getter @Setter
	private String baseURL;
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String password;

	static {
		loadProperties();
	}

	private static void loadProperties() {
		InputStream in = ReservationProperties.class.getResourceAsStream(Constants.ENVIORNMENT_PROPS);
		try {
			PROPERTIES.load(in);
		} catch (IOException e) {
			logger.error("Error while loading resource properties.");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("Error while closing resource file.");
			}
		}

	}

	public static String getPropertyValue(String key) {
		return (String) PROPERTIES.get(key);
	}
}