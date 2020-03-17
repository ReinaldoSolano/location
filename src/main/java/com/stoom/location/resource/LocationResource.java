package com.stoom.location.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.location.model.Location;
import com.stoom.location.repository.Locations;
import com.stoom.location.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationResource {
	@Autowired
	private Locations locations;
	@Autowired
	private LocationService locationService;

	@GetMapping
	public List<Location> all() {
		return locations.findAll();
	}

	@GetMapping("/{id}")
	public Location getById(@PathVariable Long id) {
		return locationService.getById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Location create(@Valid @RequestBody Location location) {
		return locationService.save(location);
	}

	@PutMapping("/{id}")
	public Location update(@PathVariable Long id, @Valid @RequestBody Location location) {
		location.setId(id);
		return locationService.save(location);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		locationService.delete(id);
	}

}
