/**
 * 
 */
package org.diveintojee.poc.webapp.testing.persistence;

import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 * 
 */
public interface UserDao {

	String BEAN_ID = "userDao";

	Map<Long, String> loadUserNamesById();

}
