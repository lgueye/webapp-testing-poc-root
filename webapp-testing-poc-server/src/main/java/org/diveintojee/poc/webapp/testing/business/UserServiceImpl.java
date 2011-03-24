/**
 *
 */
package org.diveintojee.poc.webapp.testing.business;

import java.util.List;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.diveintojee.poc.webapp.testing.domain.exceptions.BusinessException;
import org.diveintojee.poc.webapp.testing.domain.services.UserService;
import org.diveintojee.poc.webapp.testing.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author louis.gueye@gmail.com
 */
@Component(UserService.BEAN_ID)
public class UserServiceImpl implements UserService {

	@Autowired
	private PersistenceManager persistenceManager;

	@Autowired
	private UserValidator userValidator;

	/**
	 * @throws BusinessException
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#add(org.diveintojee.poc.webapp.testing.domain.User)
	 */
	@Override
	public Long add(User user) throws BusinessException {

		if (user == null)
			return null;

		this.userValidator.validate(user, UseCase.CREATE);

		return this.persistenceManager.persist(user);

	}

	/**
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#clear()
	 */
	@Override
	public void clear() {

		this.persistenceManager.clear(User.class);

	}

	/**
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {

		this.persistenceManager.delete(User.class, id);

	}

	/**
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#get(java.lang.Long)
	 */
	@Override
	public User get(Long id) {

		return this.persistenceManager.get(User.class, id);

	}

	/**
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#list()
	 */
	@Override
	public List<User> list() {

		return this.persistenceManager.findAll(User.class);

	}

	/**
	 * @throws BusinessException
	 * @see org.diveintojee.poc.webapp.testing.domain.services.UserService#update(org.diveintojee.poc.webapp.testing.domain.User)
	 */
	@Override
	public void update(User user) throws BusinessException {

		if (user == null)
			return;

		this.userValidator.validate(user, UseCase.UPDATE);

		this.persistenceManager.persist(user);

	}

}
