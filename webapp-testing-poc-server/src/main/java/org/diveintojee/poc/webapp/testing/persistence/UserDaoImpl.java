/**
 * 
 */
package org.diveintojee.poc.webapp.testing.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.diveintojee.poc.webapp.testing.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author louis.gueye@gmail.com
 * 
 */
@Repository(UserDao.BEAN_ID)
public class UserDaoImpl implements UserDao {

	@Autowired
	private PersistenceManager persistenceManager;

	/**
	 * @see org.diveintojee.poc.webapp.testing.persistence.UserDao#loadUserNameById()
	 */
	@Override
	public Map<Long, String> loadUserNamesById() {

		List<User> users = this.persistenceManager.findAll(User.class);

		if (CollectionUtils.isEmpty(users))
			return null;

		Map<Long, String> usernamesById = new HashMap<Long, String>();

		for (User user : users) {
			usernamesById.put(user.getId(), user.getUsername());
		}

		return usernamesById;
	}

}
