package com.stoom.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stoom.location.model.Location;

public interface Locations extends JpaRepository<Location, Long> {

	Optional<Location> findByLatitudeAndLongitude(Float latitude, Float Longitude);

}
