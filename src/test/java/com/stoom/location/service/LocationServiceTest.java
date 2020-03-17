package com.stoom.location.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.stoom.location.model.Location;
import com.stoom.location.repository.Locations;
import com.stoom.location.service.exception.LocationAlreadyExistsException;

public class LocationServiceTest {

	private LocationService locationService;

	@Mock
	private Locations locationsMocked;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		locationService = new LocationService(locationsMocked);
	}

	/*
	 * CREATE
	 * 
	 */
	@Test(expected = LocationAlreadyExistsException.class)
	public void should_deny_creation_of_location_that_exists() {

		Location locationInDatabase = new Location();
		locationInDatabase.setId(5L);
		locationInDatabase.setStreetName("Rua Duran");
		locationInDatabase.setNumber(250);
		locationInDatabase.setCountry("BR");
		locationInDatabase.setState("SP");
		locationInDatabase.setCity("São Paulo");
		locationInDatabase.setNeighbourhood("Cidade Ademar");

		when(locationsMocked.findByLatitudeAndLongitude(-23.661179f, -46.681427f))
				.thenReturn(Optional.of(locationInDatabase));

		Location newLocation = new Location();
		newLocation.setStreetName("Rua Duran");
		newLocation.setNumber(250);
		newLocation.setLatitude(-23.661179f);
		newLocation.setLongitude(-46.681427f);

		locationService.save(newLocation);

	}

	/*
	 * CREATE
	 * 
	 */

	@Test
	public void should_creation_new_location() {
		Location newLocation = new Location();
		newLocation.setStreetName("Rua Duran");
		newLocation.setNumber(250);
		newLocation.setLatitude(-23.661179f);
		newLocation.setLongitude(-46.681427f);

		Location newLocationInDataBase = new Location();
		newLocationInDataBase.setId(5L);
		newLocationInDataBase.setStreetName("Rua Duran");
		newLocationInDataBase.setNumber(250);
		newLocationInDataBase.setCountry("BR");
		newLocationInDataBase.setState("SP");
		newLocationInDataBase.setCity("São Paulo");
		newLocationInDataBase.setNeighbourhood("Cidade Ademar");

		when(locationsMocked.save(newLocation)).thenReturn(newLocationInDataBase);
		Location locationSaved = locationService.save(newLocation);
		assertThat(locationSaved.getId(), equalTo(5L));

	}

	/*
	 * READ
	 * 
	 */
	@Test
	public void should_getlocation() {
		Location locationInDatabase = new Location();
		locationInDatabase.setId(5L);
		locationInDatabase.setStreetName("Rua Duran");
		locationInDatabase.setNumber(250);
		locationInDatabase.setLatitude(-23.65353f);
		locationInDatabase.setLongitude(-46.5415478551f);

		when(locationsMocked.findById(5L)).thenReturn(Optional.of(locationInDatabase));

		assertThat(locationInDatabase.getId(), equalTo(5L));
		assertThat(locationInDatabase.getStreetName(), equalTo("Rua Duran"));
		assertThat(locationInDatabase.getNumber(), equalTo(250));
		assertThat(locationInDatabase.getLatitude(), equalTo(-23.65353f));
		assertThat(locationInDatabase.getLongitude(), equalTo(-46.5415478551f));
	}

	/*
	 * UPDATE
	 * 
	 */

	@Test
	public void should_update_location_number_latitude_longitude() {
		final Location locationInDatabase = new Location();
		locationInDatabase.setId(10L);
		locationInDatabase.setStreetName("Rua Duran");
		locationInDatabase.setLatitude(-23.65352f);
		locationInDatabase.setLongitude(-46.5415478545f);
		locationInDatabase.setNumber(250);

		when(locationsMocked.findById(10L)).thenReturn(Optional.of(locationInDatabase));

		final Location locationToUpdate = new Location();
		locationToUpdate.setId(10L);
		locationToUpdate.setStreetName("Rua Duran");
		locationToUpdate.setLatitude(-23.65353f);
		locationToUpdate.setLongitude(-46.5415478551f);
		locationToUpdate.setNumber(252);

		final Location locationMocked = new Location();
		locationMocked.setId(10L);
		locationMocked.setStreetName("Rua Duran");
		locationMocked.setLatitude(-23.65353f);
		locationMocked.setLongitude(-46.5415478551f);
		locationMocked.setNumber(252);

		when(locationsMocked.save(locationToUpdate)).thenReturn(locationMocked);

		final Location locationUpdated = locationService.save(locationToUpdate);
		assertThat(locationUpdated.getId(), equalTo(10L));
		assertThat(locationUpdated.getStreetName(), equalTo("Rua Duran"));
		assertThat(locationUpdated.getNumber(), equalTo(252));
		assertThat(locationUpdated.getLatitude(), equalTo(-23.65353f));
		assertThat(locationUpdated.getLongitude(), equalTo(-46.5415478551f));
	}

	/*
	 * DELETE
	 * 
	 */

	@Test
	public void should_delete_location() {
		final Location locationInDatabase = new Location();
		locationInDatabase.setId(10L);
		locationInDatabase.setStreetName("Rua Duran");

		when(locationsMocked.findById(10L)).thenReturn(Optional.of(locationInDatabase));

		locationService.delete(10L);
	}

}
