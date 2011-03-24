package org.diveintojee.poc.webapp.testing.business;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;

public interface UserValidator {

	public static final String BEAN_ID = "userValidator";

	String USERNAME_REQUIRED_ERROR_CODE = "user.username.required";

	String USERNAME_REQUIRED_DEFAULT_MESSAGE = "username is required";

	String PASSWORD_REQUIRED_ERROR_CODE = "user.password.required";

	String PASSWORD_REQUIRED_DEFAULT_MESSAGE = "password is required";

	String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";

	String VALID_EMAIL_REQUIRED_ERROR_CODE = "valid.email.required";

	String VALID_EMAIL_REQUIRED_DEFAULT_MESSAGE = "valid email required";

	String EMAIL_NOT_AVAILABLE_ERROR_CODE = "email.not.available";

	String EMAIL_NOT_AVAILABLE_DEFAULT_MESSAGE = "email already used, pick another one";

	/**
	 * - Returns true if username is not provided<br/>
	 * - Returns true if username is available<br/>
	 * - Returns false otherwise<br/>
	 * 
	 * NOTE : available means : <br/>
	 * - the username doesn't belong to any existing user, when creating account<br/>
	 * - the username doesn't belong to any existing user, excluding himself,
	 * when updating account<br/>
	 * 
	 * @param username
	 * @return
	 */
	boolean checkUsernameAvailability(Long userId, final String username);

	public void validate(final User user, UseCase useCase)
			throws BusinessException;

}