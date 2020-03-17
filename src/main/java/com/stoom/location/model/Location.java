package com.stoom.location.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {
	@Id
	@SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank(message = "locations-1")
	private String streetName;

	@NotNull(message = "locations-2")
	private Integer number;

	private String complement;

	@NotBlank(message = "locations-3")
	private String neighbourhood;

	@NotBlank(message = "locations-4")
	private String city;

	@NotBlank(message = "locations-5")
	private String state;

	@NotBlank(message = "locations-6")
	private String country;

	@NotNull(message = "locations-7")
	private Integer zipcode;

	private Float latitude;

	private Float longitude;
	
	@JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }

}
