package com.stoom.location.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoom.location.model.Location;
import com.stoom.location.repository.Locations;
import com.stoom.location.service.exception.LocationAlreadyExistsException;
import com.stoom.location.util.LocationUtil;

@Service
public class LocationService {

	private Locations locations;

	@Autowired
	private LocationUtil locationUtil;

	public LocationService(@Autowired Locations locations) {
		this.locations = locations;
	}

	public Location save(Location newLocation) {
		if (newLocation.getId() != null) {
			getById(newLocation.getId());
		}
		if (newLocation.getLatitude() == null || newLocation.getLongitude() == null) {
			newLocation = locationUtil.getLatitudeAndLongitude(newLocation);
		}

		verifyIfLocationExists(newLocation);

		return locations.save(newLocation);

	}

	public Location getById(Long id) {
		return getByOne(id).get();
	}

	public void delete(Long id) {
		Optional<Location> optionalLocation = getByOne(id);
		if (!optionalLocation.isPresent()) {
			throw new EntityNotFoundException();
		}
		locations.deleteById(id);
	}

	private void verifyIfLocationExists(final Location location) {
		Optional<Location> locationByLatitudeAndLongitude = locations.findByLatitudeAndLongitude(location.getLatitude(),
				location.getLongitude());

		if (locationByLatitudeAndLongitude.isPresent()
				&& (location.isNew() || isUpdatingToADifferentLocation(location, locationByLatitudeAndLongitude))) {
			throw new LocationAlreadyExistsException();
		}
	}

	private boolean isUpdatingToADifferentLocation(Location location,
			Optional<Location> locationByLatitudeAndLongitude) {
		return location.alreadyExist() && !locationByLatitudeAndLongitude.get().equals(location);
	}

	private Optional<Location> getByOne(Long id) {
		Optional<Location> optionalLocation = locations.findById(id);
		if (!optionalLocation.isPresent()) {
			throw new EntityNotFoundException();
		}
		return optionalLocation;
	}

}
