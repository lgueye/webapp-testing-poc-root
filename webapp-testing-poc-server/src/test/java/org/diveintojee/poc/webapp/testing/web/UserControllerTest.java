package org.diveintojee.poc.webapp.testing.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.domain.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	private final UserController underTest = new UserController();

	@Mock
	UserService userService;

	@Test
	public final void addWillRedirectToUserAccount() throws BusinessException {
		final User user = null;
		final ModelMap model = new ModelMap();
		final Long id = 1L;
		final String expectedOutCome;

		given(this.userService.add(user)).willReturn(id);
		String outcome = this.underTest.register(user, null, model);
		verify(this.userService, times(1)).add(user);

		expectedOutCome = this.underTest.redirectToUserAccount(id);

		assertEquals(expectedOutCome, outcome);
	}

	@Test
	public final void addWillRedirectToUsersListWhenUserServiceReturnsNull()
			throws BusinessException {
		final User user = null;
		final ModelMap model = new ModelMap();
		final Long id = null;
		final String expectedOutCome;

		given(this.userService.add(user)).willReturn(id);
		String outcome = this.underTest.register(user, null, model);
		verify(this.userService, times(1)).add(user);

		expectedOutCome = this.underTest.redirectToUsersList();
		assertEquals(expectedOutCome, outcome);

	}

	@Test
	public final void addWillRedisplayFormIncludingErrorWhenUserServiceThrowsBusinessException()
			throws BusinessException {
		final User user = new User();
		final ModelMap model = new ModelMap();
		String errorCode = "code";
		Object[] args = null;
		String defaultMessage = "invalid add request";
		final BusinessException ex = new BusinessException(errorCode, args,
				defaultMessage);
		given(this.userService.add(user)).willThrow(ex);
		String outcome = this.underTest.register(user, null, model);
		verify(this.userService, times(1)).add(user);
		assertEquals(UserController.NEW_FORM_VIEW_CODE, outcome);
		assertNotNull(model);
		// assertTrue(model.containsAttribute(Constants.BUSINESS_ERROR_CODE_KEY));
		// assertEquals(errorCode,
		// model.get(Constants.BUSINESS_ERROR_CODE_KEY));
	}

	@Test
	public final void deleteWillRedirectToUsersList() {
		new ModelMap();
		final Long id = null;
		final String expectedOutCome;

		String outcome = this.underTest.delete(id);
		verify(this.userService, times(1)).delete(id);
		expectedOutCome = this.underTest.redirectToUsersList();
		assertEquals(expectedOutCome, outcome);

	}

	@Test(expected = IllegalArgumentException.class)
	public final void getWillReturnThrowIllegalArgumentExceptionIfUserNotFound() {
		final ModelMap model = new ModelMap();
		final Long id = 1L;
		final User expectedUser = null;

		given(this.userService.get(id)).willReturn(expectedUser);
		this.underTest.get(id, model);
		verify(this.userService, times(1)).get(id);

	}

	@Test
	public final void newFormWillDisplayNewEmptyForm() {
		assertEquals(UserController.NEW_FORM_VIEW_CODE, this.underTest
				.newForm(new ModelMap()));
	}

	@Test
	public void testAddBusinessExceptionToModel() {

		ModelMap model = new ModelMap();
		BusinessException ex = new BusinessException("error.code", null,
				"Default message");

		this.underTest.addBusinessExceptionToModel(model, ex);

		assertNotNull(model);
		assertTrue(model.containsAttribute(Constants.BUSINESS_ERROR_CODE_KEY));
		assertEquals(ex.getMessageCode(), model
				.get(Constants.BUSINESS_ERROR_CODE_KEY));
	}

	@Test
	public void testAddThrowableToModel() {
		ModelMap model = new ModelMap();
		IllegalArgumentException th = new IllegalArgumentException(
				"Error message");

		this.underTest.addThrowableToModel(model, th);

		assertNotNull(model);
		assertTrue(model.containsAttribute(Constants.ERROR_CLASS_KEY));
		assertEquals(ClassUtils.getShortName(th.getClass()), model
				.get(Constants.ERROR_CLASS_KEY));

		assertTrue(model.containsAttribute(Constants.ERROR_MESSAGE_KEY));
		assertEquals(th.getMessage(), model.get(Constants.ERROR_MESSAGE_KEY));
	}

	@Test
	public void testAddUsersToModel() {

		final ModelMap model = new ModelMap();
		final List<User> expectedUserList = new ArrayList<User>();

		given(this.userService.list()).willReturn(expectedUserList);
		this.underTest.addUsersToModel(model);
		verify(this.userService, times(1)).list();

		assertTrue(model.containsAttribute(UserController.USERS_MODEL_KEY));
		assertEquals(expectedUserList, model
				.get(UserController.USERS_MODEL_KEY));
	}

	@Test
	public void testAddUserToModel() {

		final ModelMap model = new ModelMap();
		final Long id = 5L;
		final User expectedUser = new User();
		expectedUser.setId(id);

		given(this.userService.get(id)).willReturn(expectedUser);
		this.underTest.addUserToModel(id, model);
		verify(this.userService, times(1)).get(id);

		assertTrue(model.containsAttribute(UserController.USER_MODEL_KEY));
		assertEquals(expectedUser, model.get(UserController.USER_MODEL_KEY));
	}

	@Test
	public final void testEdit() {
		final ModelMap model = new ModelMap();
		final Long id = 1L;
		final String expectedOutCome;
		final User expectedUser = new User();
		expectedUser.setId(id);

		given(this.userService.get(id)).willReturn(expectedUser);
		String outcome = this.underTest.edit(id, model);
		verify(this.userService, times(1)).get(id);

		expectedOutCome = this.underTest.displayEditView();

		assertEquals(expectedOutCome, outcome);
		assertNotNull(model);
		assertTrue(model.containsAttribute(UserController.USER_MODEL_KEY));
		assertEquals(expectedUser, model.get(UserController.USER_MODEL_KEY));
	}

	@Test
	public final void testGet() {
		final ModelMap model = new ModelMap();
		final Long id = 1L;
		final String expectedOutCome;
		final User expectedUser = new User();
		expectedUser.setId(id);

		given(this.userService.get(id)).willReturn(expectedUser);
		String outcome = this.underTest.get(id, model);
		verify(this.userService, times(1)).get(id);

		expectedOutCome = this.underTest.displayAccountView();

		assertEquals(expectedOutCome, outcome);
		assertNotNull(model);
		assertTrue(model.containsAttribute(UserController.USER_MODEL_KEY));
		assertEquals(expectedUser, model.get(UserController.USER_MODEL_KEY));
	}

	@Test
	public final void testList() {
		final ModelMap model = new ModelMap();
		final String expectedOutCome;
		final List<User> expectedUserList = new ArrayList<User>();

		given(this.userService.list()).willReturn(expectedUserList);
		String outcome = this.underTest.list(model);
		verify(this.userService, times(1)).list();

		expectedOutCome = this.underTest.displayListView();
		assertEquals(expectedOutCome, outcome);

		assertNotNull(model);
		assertTrue(model.containsAttribute(UserController.USERS_MODEL_KEY));
		assertEquals(expectedUserList, model
				.get(UserController.USERS_MODEL_KEY));
	}

	@Test
	public final void updateWillRedirectToUserAccount()
			throws BusinessException {
		final Long id = 1L;
		final User user = new User();
		user.setId(id);
		final ModelMap model = new ModelMap();
		final String expectedOutCome;

		String outcome = this.underTest.update(user, null, model);
		verify(this.userService, times(1)).update(user);

		expectedOutCome = this.underTest.redirectToUserAccount(id);

		assertEquals(expectedOutCome, outcome);
	}

	@Test
	public final void updateWillRedisplayFormIncludingErrorWhenUserServiceThrowsBusinessException()
			throws BusinessException {
		final User user = new User();
		final ModelMap model = new ModelMap();
		String errorCode = "code";
		Object[] args = null;
		String defaultMessage = "invalid add request";
		BindingResult bindingResult = new BindException(user,
				UserController.USER_MODEL_KEY);

		BusinessException ex = new BusinessException(errorCode, args,
				defaultMessage);

		doThrow(ex).when(this.userService).update(user);
		String outcome = this.underTest.update(user, bindingResult, model);
		verify(this.userService, times(1)).update(user);
		assertEquals(UserController.EDIT_VIEW_CODE, outcome);
		assertNotNull(model);
		// assertTrue(model.containsAttribute(Constants.BUSINESS_ERROR_CODE_KEY));
		// assertEquals(errorCode,
		// model.get(Constants.BUSINESS_ERROR_CODE_KEY));
	}

}
