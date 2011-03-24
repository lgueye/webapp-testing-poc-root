package org.diveintojee.poc.webapp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * @author louis.gueye@gmail.com
 */
public class UserFunctionnalTest {

	HttpClient httpClient;

	private static final String BASE_URL = ResourceBundle.getBundle(
			Constants.CONFIG_BUNDLE_NAME).getString(Constants.SERVER_URL_KEY);

	private static final String SERVER_MODULE_CONTEXT = ResourceBundle
			.getBundle(Constants.CONFIG_BUNDLE_NAME).getString(
					Constants.SERVER_MODULE_CONTEXT_KEY);

	private static final String JETTY_PORT = ResourceBundle.getBundle(
			Constants.CONFIG_BUNDLE_NAME).getString(Constants.JETTY_PORT_KEY);

	private static Server server;

	/**
	 * @throws Throwable
	 */
	@AfterClass
	public static void afterClass() throws Throwable {

		server.stop();
		server.join();

	}

	/**
	 * @throws Throwable
	 */
	@BeforeClass
	public static void beforeClass() throws Throwable {

		server = new Server();

		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMaxThreads(100);
		server.setThreadPool(threadPool);

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(Integer.valueOf(JETTY_PORT));
		connector.setMaxIdleTime(30000);
		connector.setConfidentialPort(8443);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webappTestingServerWebApp = new WebAppContext();
		webappTestingServerWebApp
				.setWar("../webapp-testing-poc-server/target/webapp-testing-poc-server.war");
		webappTestingServerWebApp.setContextPath("/" + SERVER_MODULE_CONTEXT);

		server.addHandler(webappTestingServerWebApp);

		server.start();

		server.setStopAtShutdown(true);

		server.setSendServerVersion(true);

	}

	/**
	 * @throws Throwable
	 */

	@Test
	public void addUserOk() throws Throwable {

		String uri = null;

		HttpMethod httpMethod = null;

		NameValuePair[] queryString = null;

		int statusCode;

		String username = "louis.gueye@gmail.com";

		String password = "secret";

		uri = "/users/register";

		String url = BASE_URL + uri;
		httpMethod = new PutMethod(url);

		httpMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		queryString = new NameValuePair[] {
				new NameValuePair("username", username),
				new NameValuePair("password", password) };

		httpMethod.setQueryString(queryString);

		statusCode = this.httpClient.executeMethod(httpMethod);

		System.out.println(httpMethod.getResponseBodyAsString());

		assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, statusCode);

	}

	@Test
	@Ignore
	public final void addWillDisplayUserAccount() throws Throwable {

		WebConversation conversation = new WebConversation();

		WebResponse response = null;

		String uri = "/users/new";

		String url = BASE_URL + uri;

		WebRequest request = new GetMethodWebRequest(url);

		response = conversation.getResponse(request);

		assertNotNull(response);

		WebForm form = response.getForms()[0];

		assertNotNull(form);

		String username = "manu@diveintojee.org";

		String password = "secret";

		String idKey = "id";

		String usernameKey = "username";

		String passwordKey = "password";

		form.setParameter(usernameKey, username);

		form.setParameter(passwordKey, password);

		form.submit();

		response = conversation.getCurrentPage();

		System.out.println(response.getText());

		assertNotNull(response);

		String expectedTitle = ResourceBundle.getBundle(
				Constants.MESSAGES_BUNDLE_NAME).getString(
				Constants.INDEX_PAGE_TITLE_KEY);

		assertEquals(expectedTitle, response.getTitle().trim());

		String documentAsString = response.getText();

		assertNotNull(documentAsString);

		FunctionnalTestsFixtures.assertDocumentContainsNodeAtPath(
				documentAsString, "//div/div[@id='" + idKey + "']");

		FunctionnalTestsFixtures.assertDocumentContainsValueAtPath(
				documentAsString, "//div//div[@id='" + usernameKey + "']",
				username);

		FunctionnalTestsFixtures.assertDocumentContainsValueAtPath(
				documentAsString, "//div//div[@id='" + passwordKey + "']",
				password);

	}

	/**
	 * @throws Throwable
	 */

	@Test
	public void appIsUp() throws Throwable {

		HttpMethod httpMethod = null;

		int statusCode;

		String uri = "/";

		String url = BASE_URL + uri;

		httpMethod = new GetMethod(url);

		statusCode = this.httpClient.executeMethod(httpMethod);

		assertEquals(HttpStatus.SC_OK, statusCode);

		String documentAsString = httpMethod.getResponseBodyAsString();

		String expectedValue = ResourceBundle.getBundle(
				Constants.MESSAGES_BUNDLE_NAME).getString(
				Constants.INDEX_PAGE_TITLE_KEY);

		FunctionnalTestsFixtures.assertCorrectPageTitleIsDisplayed(
				documentAsString, expectedValue);

	}

	/**
	 * 
	 */
	@Before
	public void before() {

		this.httpClient = new HttpClient();

	}

	@Test
	public final void listWillDisplayListPage() throws Throwable {

		HttpMethod httpMethod = null;

		String uri = null;

		int statusCode;

		uri = "/users/";

		String url = BASE_URL + uri;

		httpMethod = new GetMethod(url);

		statusCode = this.httpClient.executeMethod(httpMethod);

		assertEquals(HttpStatus.SC_OK, statusCode);

		String documentAsString = httpMethod.getResponseBodyAsString();

		String expectedTitle = ResourceBundle.getBundle(
				Constants.MESSAGES_BUNDLE_NAME).getString(
				Constants.INDEX_PAGE_TITLE_KEY);

		FunctionnalTestsFixtures.assertCorrectPageTitleIsDisplayed(
				documentAsString, expectedTitle);

		FunctionnalTestsFixtures.assertDocumentContainsValueAtPath(
				documentAsString, "//body//table/tr[1]/thead/th[1]", "Id");

		FunctionnalTestsFixtures
				.assertDocumentContainsValueAtPath(documentAsString,
						"//body//table/tr[1]/thead/th[2]", "Username");

		FunctionnalTestsFixtures
				.assertDocumentContainsValueAtPath(documentAsString,
						"//body//table/tr[1]/thead/th[3]", "Password");

	}

	@Test
	public final void newWillDisplayEmptyForm() throws Throwable {

		HttpMethod httpMethod = null;

		String uri = null;

		int statusCode;

		uri = "/users/new";

		String url = BASE_URL + uri;

		httpMethod = new GetMethod(url);

		statusCode = this.httpClient.executeMethod(httpMethod);

		assertEquals(HttpStatus.SC_OK, statusCode);

		String documentAsString = httpMethod.getResponseBodyAsString();

		String expectedTitle = ResourceBundle.getBundle(
				Constants.MESSAGES_BUNDLE_NAME).getString(
				Constants.INDEX_PAGE_TITLE_KEY);

		FunctionnalTestsFixtures.assertCorrectPageTitleIsDisplayed(
				documentAsString, expectedTitle);

		FunctionnalTestsFixtures.assertDocumentContainsNodeAtPath(
				documentAsString, "//form//input[@name='username']");

		FunctionnalTestsFixtures.assertDocumentContainsNodeAtPath(
				documentAsString, "//form//input[@name='password']");
	}
}
