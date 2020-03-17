package com.stoom.location.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.LatLng;
import com.google.gson.Gson;
import com.stoom.location.error.InternalServerErrorException;
import com.stoom.location.model.Location;

@Component
public class LocationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationUtil.class);

	private static final String SEPARATOR = "+";
	private static final String TERMINATOR = "&key=";

	@Value("${google.url}")
	private String urlGoogle;

	@Value("${google.key}")
	private String keyGoogle;

	public Location getLatitudeAndLongitude(Location l) {
		String address = l.getNumber().toString() + SEPARATOR + l.getStreetName() + SEPARATOR + l.getNeighbourhood()
				+ SEPARATOR + l.getCity() + SEPARATOR + l.getCountry() + TERMINATOR;

		try {
			String url = urlGoogle + address + keyGoogle;

			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(url, String.class);

			Gson gson = new Gson();
			GeocodeResponse g = gson.fromJson(result, GeocodeResponse.class);
			LatLng loc = g.getResults().get(0).getGeometry().getLocation();

			l.setLatitude(new BigDecimal(loc.getLat().toString()).floatValue());
			l.setLongitude(new BigDecimal(loc.getLng().toString()).floatValue());

		} catch (RestClientException e) {
			LOGGER.error("Google API error");
			throw new InternalServerErrorException();

		} catch (Exception e) {
			throw new InternalServerErrorException();

		}

		return l;
	}

}
