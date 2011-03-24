/**
 * 
 */
package org.diveintojee.poc.webapp.testing.business;

import static org.junit.Assert.assertNull;

import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.domain.services.UserService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author louis.gueye@gmail.com
 *
 */
public class UserServiceImplTest {

	private UserService underTest;

	/**
	 * Test method for
	 * {@link org.diveintojee.poc.webapp.testing.business.UserServiceImpl#add(org.diveintojee.poc.webapp.testing.domain.User)}
	 * .
	 * 
	 * @throws BusinessException
	 */
	@Test
	public final void addWillReturnNullIfNullUserProvided()
			throws BusinessException {
		assertNull(this.underTest.add(null));
	}

	@Before
	public void before() {
		this.underTest = new UserServiceImpl();
	}

}
