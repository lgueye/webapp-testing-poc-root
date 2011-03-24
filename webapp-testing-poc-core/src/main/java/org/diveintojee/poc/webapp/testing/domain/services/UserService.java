/**
 *
 */
package org.diveintojee.poc.webapp.testing.domain.services;

import java.util.List;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;

/**
 * @author louis.gueye@gmail.com
 */
public interface UserService {

	String BEAN_ID = "userService";

	/**
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	Long add(User user) throws BusinessException;

	/**
	 *
	 */
	void clear();

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * @param product
	 * @return
	 */
	User get(Long id);

	/**
	 * @return
	 */
	List<User> list();

	/**
	 * @param user
	 * @throws BusinessException
	 */
	void update(User user) throws BusinessException;
}
