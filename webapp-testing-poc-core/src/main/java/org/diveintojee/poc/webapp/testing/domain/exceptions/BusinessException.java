/**
 * 
 */
package org.diveintojee.poc.webapp.testing.domain.exceptions;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.diveintojee.poc.webapp.testing.domain.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author louis.gueye@gmail.com
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BusinessException.class);
	
	private String messageCode;
	private Object[] messageArgs;
	private String defaultMessage;
	private Locale locale;
	private Throwable cause;

	public BusinessException(String message) {
		super(message);
	}
	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param messageCode the messageCode to set
	 */
	private void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return the defaultMessage
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	/**
	 * @param defaultMessage the defaultMessage to set
	 */
	private void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	/**
	 * @return the messageArgs
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * @param messageArgs the messageArgs to set
	 */
	private void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	private void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	private void setCause(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * @param messageCode
	 * @param messageArgs
	 * @param defaultMessage
	 */
	public BusinessException(String messageCode, Object[] messageArgs, String defaultMessage) {
		setMessageCode(messageCode);
		setMessageArgs(messageArgs);
		setDefaultMessage(defaultMessage);
	}
	/**
	 * @param messageCode
	 * @param messageArgs
	 * @param defaultMessage
	 * @param locale
	 */
	public BusinessException(String messageCode, Object[] messageArgs, String defaultMessage, Locale locale) {
		this(messageCode, messageArgs, defaultMessage);
		setLocale(locale);
	}
	
	/**
	 * @param messageCode
	 * @param messageArgs
	 * @param defaultMessage
	 * @param cause
	 */
	public BusinessException(String messageCode, Object[] messageArgs, String defaultMessage, Throwable cause) {
		this(messageCode, messageArgs, defaultMessage);
		setCause(cause);
	}

	/**
	 * @param messageCode
	 * @param messageArgs
	 * @param defaultMessage
	 * @param cause
	 * @param locale
	 */
	public BusinessException(String messageCode, Object[] messageArgs, String defaultMessage, Throwable cause, Locale locale) {
		this(messageCode, messageArgs, defaultMessage, locale);
		setCause(cause);
	}
	
	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {

		if (StringUtils.isEmpty(getMessageCode())) return getDefaultMessage();

		Locale locale = getLocale() == null ? Locale.getDefault() : getLocale();

		ResourceBundle bundle = null;

		try {

			bundle = ResourceBundle.getBundle(Constants.MESSAGES_BUNDLE_NAME, locale);

		} catch (MissingResourceException e) {

			LOG.debug("Bundle 'message' not found for locale '" + locale.getLanguage() + "'. Using default message");

			return getDefaultMessage();

		}

		String message = null;

		try {

			message = bundle.getString(getMessageCode());

		} catch (MissingResourceException e) {

			LOG.debug("Message not found for key '" + getMessageCode() + "'. Using default message");

			return getDefaultMessage();

		}

		if (!ArrayUtils.isEmpty(getMessageArgs())) {

			message = MessageFormat.format(message, getMessageArgs());

		}

		return message;
	}
}
