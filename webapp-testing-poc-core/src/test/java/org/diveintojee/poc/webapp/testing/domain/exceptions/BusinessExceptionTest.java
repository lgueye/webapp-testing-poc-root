/**
 * 
 */
package org.diveintojee.poc.webapp.testing.domain.exceptions;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.diveintojee.poc.webapp.testing.domain.Constants;
import org.junit.Test;

/**
 * @author louis.gueye@gmail.com
 *
 */
public class BusinessExceptionTest {

	/**
	 * Test method for {@link org.deepintoj2ee.wishlister.domain.exceptions.BusinessException#getMessage()}.
	 */
	@Test
	public final void testGetMessage() {
		String messageCode = "test.code";
		Object[] messageArgs = new Object[]{"sdfsdf", 5L};
		String defaultMessage = "default message";
		Locale locale = Locale.ENGLISH;
		Throwable cause = new Throwable("bla bla");
		BusinessException e = null;
		
		e = new BusinessException(null, messageArgs, defaultMessage, cause, locale);
		Assert.assertEquals(e.getDefaultMessage(), e.getMessage());
		
		String rawMessage = ResourceBundle.getBundle(Constants.MESSAGES_BUNDLE_NAME).getString(messageCode);
		e = new BusinessException(messageCode, null, defaultMessage, cause, locale);
		Assert.assertEquals(rawMessage, e.getMessage());
		
		e = new BusinessException(messageCode, messageArgs, defaultMessage, cause, locale);
		Assert.assertEquals(MessageFormat.format(rawMessage, messageArgs), e.getMessage());
	}

}
