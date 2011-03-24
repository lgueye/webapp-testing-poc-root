/**
 *
 */
package org.diveintojee.poc.webapp.testing.web;

import java.util.List;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author louis.gueye@gmail.com
 * 
 */
@Controller(UserController.BEAN_ID)
public class UserController {

	public static final String BEAN_ID = "userController";

	@Autowired
	private UserService userService;

	public static final String USERS_MODEL_KEY = "users";

	public static final String LIST_VIEW_CODE = USERS_MODEL_KEY + "/list";

	public static final String EDIT_VIEW_CODE = USERS_MODEL_KEY
			+ "/edit-account";

	public static final String ACCOUNT_VIEW_CODE = USERS_MODEL_KEY
			+ "/view-account";

	public static final String NEW_FORM_VIEW_CODE = USERS_MODEL_KEY
			+ "/new-form";

	public static final String USER_MODEL_KEY = "user";

	public static final String ID_FIELD_KEY = "id";

	public static final String LIST_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY;

	public static final String GET_URI = "load";

	public static final String GET_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + Constants.SLASH + GET_URI + "/{id}";

	public static final String ADD_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + Constants.SLASH + "register";

	public static final String NEW_FORM_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + Constants.SLASH + "new";

	public static final String EDIT_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + Constants.SLASH + "edit/{id}";

	public static final String UPDATE_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + Constants.SLASH + "update/{id}";

	public static final String DELETE_URI_PATTERN = Constants.SLASH
			+ USERS_MODEL_KEY + "/delete/{id}";

	protected void addBusinessExceptionToModel(ModelMap model,
			BusinessException ex) {

		model.addAttribute(Constants.BUSINESS_ERROR_CODE_KEY, ex
				.getMessageCode());

	}

	protected void addThrowableToModel(ModelMap model, Throwable th) {

		model.addAttribute(Constants.ERROR_CLASS_KEY, ClassUtils
				.getShortName(th.getClass()));

		model.addAttribute(Constants.ERROR_MESSAGE_KEY, th.getMessage());

	}

	protected void addUsersToModel(ModelMap model) {

		List<User> users = this.userService.list();

		model.addAttribute(USERS_MODEL_KEY, users);

	}

	protected void addUserToModel(Long id, ModelMap model) {

		User persistedUser = this.userService.get(id);

		model.addAttribute(USER_MODEL_KEY, persistedUser);

	}

	/**
	 * @param user
	 */
	@RequestMapping(value = DELETE_URI_PATTERN, method = RequestMethod.DELETE)
	public String delete(@PathVariable(ID_FIELD_KEY) Long id) {

		this.userService.delete(id);

		return redirectToUsersList();

	}

	protected String displayAccountView() {
		return ACCOUNT_VIEW_CODE;
	}

	protected String displayEditView() {
		return EDIT_VIEW_CODE;
	}

	protected String displayListView() {
		return LIST_VIEW_CODE;
	}

	protected String displayNewFormView() {
		return NEW_FORM_VIEW_CODE;
	}

	/**
	 * @param product
	 * @return
	 */
	@RequestMapping(value = EDIT_URI_PATTERN, method = RequestMethod.GET)
	public String edit(@PathVariable(ID_FIELD_KEY) Long id, ModelMap model) {

		addUserToModel(id, model);

		return displayEditView();

	}

	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = GET_URI_PATTERN, method = RequestMethod.GET)
	public String get(@PathVariable(ID_FIELD_KEY) Long id, ModelMap model) {

		addUserToModel(id, model);

		User user = (User) model.get(UserController.USER_MODEL_KEY);

		if (user == null)
			throw new IllegalArgumentException("User with ID " + id
					+ "not found");

		return displayAccountView();

	}

	/**
	 * @return
	 */
	@RequestMapping(value = { LIST_URI_PATTERN,
			LIST_URI_PATTERN + Constants.SLASH }, method = RequestMethod.GET)
	public String list(ModelMap model) {

		addUsersToModel(model);

		return displayListView();

	}

	/**
	 * @param product
	 * @return
	 */
	@RequestMapping(value = NEW_FORM_URI_PATTERN, method = RequestMethod.GET)
	public String newForm(ModelMap model) {

		model.addAttribute(USER_MODEL_KEY, new User());

		return displayNewFormView();

	}

	protected String redirectToUserAccount(Long id) {

		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + Constants.SLASH
				+ UserController.USERS_MODEL_KEY + Constants.SLASH + GET_URI
				+ Constants.SLASH + id;

	}

	protected String redirectToUsersList() {

		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + Constants.SLASH
				+ UserController.USERS_MODEL_KEY;

	}

	/**
	 * @param user
	 * @return
	 */
	@RequestMapping(value = ADD_URI_PATTERN, method = RequestMethod.PUT)
	public String register(@ModelAttribute(USER_MODEL_KEY) User user,
			BindingResult bindingResult, ModelMap model) {

		Long id = null;

		try {

			id = this.userService.add(user);

		} catch (BusinessException ex) {

			if (bindingResult == null) {
				bindingResult = new BindException(user,
						UserController.USER_MODEL_KEY);
			}

			bindingResult.reject(ex.getMessageCode(), ex.getMessageArgs(), ex
					.getDefaultMessage());

			return displayNewFormView();

		}

		if (id == null)
			return redirectToUsersList();

		return redirectToUserAccount(id);

	}

	/**
	 * @param user
	 */
	@RequestMapping(value = UPDATE_URI_PATTERN, method = RequestMethod.POST)
	public String update(@ModelAttribute(USER_MODEL_KEY) User user,
			BindingResult bindingResult, ModelMap model) {

		try {

			this.userService.update(user);

			return redirectToUserAccount(user.getId());

		} catch (BusinessException ex) {

			if (bindingResult == null) {
				bindingResult = new BindException(user,
						UserController.USER_MODEL_KEY);
			}

			bindingResult.reject(ex.getMessageCode(), ex.getMessageArgs(), ex
					.getDefaultMessage());

			return displayEditView();

		}

	}
}
