/**
 * 
 */
package org.diveintojee.poc.webapp.testing.business;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author louis.gueye@gmail.com
 * 
 */
@Component(UserValidatorImpl.BEAN_ID)
public class UserValidatorImpl implements UserValidator {

	@Autowired
	private UserDao userDao;

	/**
	 * @see org.diveintojee.poc.webapp.testing.business.UserValidator#checkUsernameAvailability(java.lang.Long,
	 * java.lang.String)
	 */
	public boolean checkUsernameAvailability(final Long id,
			final String username) {

		if (StringUtils.isEmpty(username))
			return true;

		Map<Long, String> usernamesById = this.userDao.loadUserNamesById();

		if (MapUtils.isEmpty(usernamesById))
			return true;

		if (id == null && usernamesById.values().contains(username))
			return false;

		if (id != null) {
			String actualUserName = usernamesById.get(id);
			return username.equals(actualUserName)
					|| !usernamesById.values().contains(username);
		}

		return true;

	}

	protected void commonValidations(final User user) throws BusinessException {

		if (StringUtils.isEmpty(user.getUsername()))
			throw new BusinessException(
					UserValidator.USERNAME_REQUIRED_ERROR_CODE, null,
					UserValidator.USERNAME_REQUIRED_DEFAULT_MESSAGE);

		String username = user.getUsername();

		if (!checkUsernameAvailability(user.getId(), username))
			throw new BusinessException(
					UserValidator.EMAIL_NOT_AVAILABLE_ERROR_CODE,
					new Object[] { user.getUsername() },
					UserValidator.EMAIL_NOT_AVAILABLE_DEFAULT_MESSAGE);

		if (!Pattern.matches(UserValidator.EMAIL_PATTERN, username
				.toUpperCase()))
			throw new BusinessException(
					UserValidator.VALID_EMAIL_REQUIRED_ERROR_CODE,
					new Object[] { user.getUsername() },
					UserValidator.VALID_EMAIL_REQUIRED_DEFAULT_MESSAGE);

		// Password policy begins here

		if (StringUtils.isEmpty(user.getPassword()))
			throw new BusinessException(
					UserValidator.PASSWORD_REQUIRED_ERROR_CODE,
					new Object[] { user.getUsername() },
					UserValidator.PASSWORD_REQUIRED_DEFAULT_MESSAGE);

		// Extend password policy here
		// For example : pwd must be between 8 and 12 characters long
		// For example : pwd must not match previous password
		// For example : pwd must contain at least one digit
		// For example : pwd must contain at least one special char
		// {&,[,],#,~,|,`,{,},-,+,/,\,*,@}
	}

	protected void createValidations(User user) throws BusinessException {

		if (user == null)
			throw new IllegalStateException(
					"User should not be null for update");

		commonValidations(user);

	}

	protected void updateValidations(User user) throws BusinessException {

		if (user == null)
			throw new IllegalStateException(
					"User should not be null for update");

		if (user.getId() == null)
			throw new IllegalStateException("Id should not be null for update");

		commonValidations(user);

	}

	/**
	 * @see org.diveintojee.poc.webapp.testing.business.UserValidator#validate(org.diveintojee.poc.webapp.testing.domain.User,
	 * org.diveintojee.poc.webapp.testing.business.UserValidatorImpl.UseCase)
	 */
	public void validate(final User user, UseCase useCase)
			throws BusinessException {

		if (useCase == null) {
			commonValidations(user);
		}
		switch (useCase) {
		case UPDATE:
			updateValidations(user);
			break;
		case CREATE:
			createValidations(user);
			break;
		default:
			commonValidations(user);
		}
	}
}
