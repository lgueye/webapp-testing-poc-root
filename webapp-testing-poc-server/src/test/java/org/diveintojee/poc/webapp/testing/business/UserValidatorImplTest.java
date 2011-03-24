package org.diveintojee.poc.webapp.testing.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.persistence.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorImplTest {

	@InjectMocks
	private final UserValidator underTest = new UserValidatorImpl();

	@Mock
	UserDao userDao;

	@Test
	public final void checkUsernameAvailabilityWillReturnFalseWhenRepositoryContainsProvidedUsername() {
		final Long id = null;
		final String username = "test";
		Map<Long, String> usernamesById;
		boolean usernameIsAvailable;

		usernamesById = new HashMap<Long, String>();
		usernamesById.put(id, username);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertFalse(usernameIsAvailable);
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnFalseWhenUserCreateAndUsernameIsNotAvailable() {
		final Long id = null;
		final String username = "test";
		Map<Long, String> usernamesById;
		boolean usernameIsAvailable;

		usernamesById = new HashMap<Long, String>();
		usernamesById.put(2L, username);

		reset(this.userDao);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertFalse(usernameIsAvailable);
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnFalseWhenUserUpdateAndUsernameIsNotAvailable() {
		final Long id = 1L;
		final String username = "test";
		Map<Long, String> usernamesById;
		boolean usernameIsAvailable;

		usernamesById = new HashMap<Long, String>();
		usernamesById.put(1L, "previousUserName");
		usernamesById.put(2L, username);

		reset(this.userDao);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertFalse(usernameIsAvailable);
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnTrueWhenEmptyUsernameProvided() {
		assertTrue(this.underTest.checkUsernameAvailability(null,
				StringUtils.EMPTY));
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnTrueWhenNullUsernameProvided() {
		assertTrue(this.underTest.checkUsernameAvailability(null, null));
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnTrueWhenRepositoryContainsNoUsernames() {
		final Long id = null;
		final String username = "test";
		Map<Long, String> usernamesById;
		boolean usernameIsAvailable;

		usernamesById = null;
		reset(this.userDao);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertTrue(usernameIsAvailable);

		usernamesById = new HashMap<Long, String>();
		reset(this.userDao);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertTrue(usernameIsAvailable);
	}

	@Test
	public final void checkUsernameAvailabilityWillReturnTrueWhenUserUpdateAndUsernameIsAvailable() {
		final Long id = 1L;
		final String username = "test";
		Map<Long, String> usernamesById;
		boolean usernameIsAvailable;

		usernamesById = new HashMap<Long, String>();
		usernamesById.put(1L, "previousUserName");
		// usernamesById.put(2L, username);

		reset(this.userDao);
		given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
		usernameIsAvailable = this.underTest.checkUsernameAvailability(id,
				username);
		verify(this.userDao, times(1)).loadUserNamesById();
		assertTrue(usernameIsAvailable);
	}

	@Test
	public final void commonValidationsWillThrowBusinessExceptionWhenEmptyPasswordProvided() {
		final User user = new User();
		String username = "louis.gueye@gmail.com";
		user.setUsername(username);
		String password = StringUtils.EMPTY;
		user.setPassword(password);

		final Map<Long, String> usernamesById = new HashMap<Long, String>();
		try {
			given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
			((UserValidatorImpl) this.underTest).commonValidations(user);
			verify(this.userDao, times(1)).loadUserNamesById();
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.PASSWORD_REQUIRED_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void commonValidationsWillThrowBusinessExceptionWhenEmptyUsernameProvided() {
		final User user = new User();
		String username = StringUtils.EMPTY;
		user.setUsername(username);
		try {
			((UserValidatorImpl) this.underTest).commonValidations(user);
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.USERNAME_REQUIRED_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void commonValidationsWillThrowBusinessExceptionWhenInvalidUsernameProvided() {
		final User user = new User();
		String username = "a@b";
		user.setUsername(username);
		try {
			((UserValidatorImpl) this.underTest).commonValidations(user);
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.VALID_EMAIL_REQUIRED_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void commonValidationsWillThrowBusinessExceptionWhenNullPasswordProvided() {
		final User user = new User();
		String username = "louis.gueye@gmail.com";
		user.setUsername(username);
		final Map<Long, String> usernamesById = new HashMap<Long, String>();
		try {
			given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
			((UserValidatorImpl) this.underTest).commonValidations(user);
			verify(this.userDao, times(1)).loadUserNamesById();
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.PASSWORD_REQUIRED_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void commonValidationsWillThrowBusinessExceptionWhenNullUsernameProvided() {
		final User user = new User();
		try {
			((UserValidatorImpl) this.underTest).commonValidations(user);
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.USERNAME_REQUIRED_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void createValidationsWillThrowBusinessExceptionWhenUsernameNotAvailable() {
		final User user = new User();
		Long id = 1L;
		String username = "louis@gmail.com";
		String password = "secret";
		// user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		final Map<Long, String> usernamesById = new HashMap<Long, String>();
		usernamesById.put(id, username);
		try {
			given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
			((UserValidatorImpl) this.underTest).createValidations(user);
			verify(this.userDao, times(1)).loadUserNamesById();
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.EMAIL_NOT_AVAILABLE_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test
	public final void updateValidationsWillThrowBusinessExceptionWhenUsernameNotAvailable() {
		final User user = new User();
		Long id = 1L;
		String username = "louis@gmail.com";
		String password = "secret";
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		final Map<Long, String> usernamesById = new HashMap<Long, String>();
		usernamesById.put(id, "fabian@fenaut.fr");
		usernamesById.put(2L, username);
		try {
			given(this.userDao.loadUserNamesById()).willReturn(usernamesById);
			((UserValidatorImpl) this.underTest).updateValidations(user);
			verify(this.userDao, times(1)).loadUserNamesById();
			fail("BusinessException expected");
		} catch (BusinessException ex) {
			assertEquals(UserValidator.EMAIL_NOT_AVAILABLE_ERROR_CODE, ex
					.getMessageCode());
		} catch (Throwable th) {
			fail("BusinessException expected, got " + th);
		}
	}

	@Test(expected = IllegalStateException.class)
	public final void updateValidationsWillThrowIllegalStateExceptionWhenNullUserIdProvided()
			throws BusinessException {
		((UserValidatorImpl) this.underTest).updateValidations(new User());
	}

	@Test(expected = IllegalStateException.class)
	public final void updateValidationsWillThrowIllegalStateExceptionWhenNullUserProvided()
			throws BusinessException {
		((UserValidatorImpl) this.underTest).updateValidations(null);
	}
}
