package org.diveintojee.poc.webapp.testing.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.diveintojee.poc.webapp.testing.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

	@InjectMocks
	private final UserDao underTest = new UserDaoImpl();

	@Mock
	PersistenceManager persistenceManager;

	@Test
	public final void loadUserNamesOk() {
		final Long firstId = 9L;
		final String fisrtUserName = "a.b@c.com";
		final User firstUser = new User();
		firstUser.setId(firstId);
		firstUser.setUsername(fisrtUserName);
		final Long secondId = 5L;
		final String secondUserName = "d.e@f.com";
		final User secondUser = new User();
		secondUser.setId(secondId);
		secondUser.setUsername(secondUserName);
		List<User> users = Arrays.asList(new User[] { firstUser, secondUser });

		given(this.persistenceManager.findAll(User.class)).willReturn(users);
		Map<Long, String> result = this.underTest.loadUserNamesById();
		verify(this.persistenceManager, times(1)).findAll(User.class);
		assertNotNull(result);
		assertEquals(users.size(), result.size());
	}

	@Test
	public final void loadUserNamesWillInvokeOnceAndOnceOnlyPersistenceManager() {
		this.underTest.loadUserNamesById();
		verify(this.persistenceManager, times(1)).findAll(User.class);
	}

	@Test
	public final void loadUserNamesWillReturnNullWithEmptyUsersList() {
		given(this.persistenceManager.findAll(User.class)).willReturn(
				new ArrayList<User>());
		Map<Long, String> result = this.underTest.loadUserNamesById();
		verify(this.persistenceManager, times(1)).findAll(User.class);
		assertNull(result);
	}

	@Test
	public final void loadUserNamesWillReturnNullWithNullUsersList() {
		given(this.persistenceManager.findAll(User.class)).willReturn(null);
		Map<Long, String> result = this.underTest.loadUserNamesById();
		verify(this.persistenceManager, times(1)).findAll(User.class);
		assertNull(result);
	}
}
