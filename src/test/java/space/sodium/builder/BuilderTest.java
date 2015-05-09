package space.sodium.builder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import space.sodium.builder.Builder;
import space.sodium.entity.model.Address;
import space.sodium.entity.model.User;
import space.sodium.entity.rep.AddressRep;
import space.sodium.entity.rep.UserRep;

public class BuilderTest {
	
	Builder builder;
	
	@Before
	public void before() {
		builder = new Builder();
		builder.register(User.class, UserRep.class);
		builder.register(Address.class, AddressRep.class);
	}
	
	@Test
	public void testConvertToRep() throws Exception {
		
		Address address = new Address();
		address.setCity("San Francisco");
		address.setState("CA");
		address.setZip(94104);
		
		User user = new User();
		user.setAddress(address);
		user.setFirstName("Andrew");
		user.setId(1L);
		user.setLastName("Dudley");
		
		UserRep userRep = (UserRep) builder.getRep(user);
		
		Assert.assertEquals(1L, userRep.getId().longValue());
		Assert.assertEquals("Andrew", userRep.getFirstName());
		Assert.assertEquals("Dudley", userRep.getLastName());
		Assert.assertNull(userRep.getAddress().getStreet());
		Assert.assertEquals("San Francisco", userRep.getAddress().getCity());
		Assert.assertEquals("San Francisco", userRep.getAddressCity());
		Assert.assertEquals("CA", userRep.getAddress().getState());
		Assert.assertEquals(94104L, userRep.getAddress().getZip().longValue());
		Assert.assertNull(userRep.getComputedField());
	}
}
