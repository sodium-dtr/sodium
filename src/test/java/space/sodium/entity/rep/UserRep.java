package space.sodium.entity.rep;

import space.sodium.entity.Representation;
import space.sodium.entity.Represents;

@Representation
public class UserRep extends Object {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private AddressRep address;
	
	@Represents("address.city")
	private String addressCity;
	
	private String computedField;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AddressRep getAddress() {
		return address;
	}

	public void setAddress(AddressRep address) {
		this.address = address;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getComputedField() {
		return computedField;
	}

	public void setComputedField(String computedField) {
		this.computedField = computedField;
	}
}
