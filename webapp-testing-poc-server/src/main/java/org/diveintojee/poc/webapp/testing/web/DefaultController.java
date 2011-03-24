/**
 *
 */
package org.diveintojee.poc.webapp.testing.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author louis.gueye@gmail.com
 * 
 */
@Controller(DefaultController.BEAN_ID)
public class DefaultController {

	public static final String BEAN_ID = "defaultController";

	/**
	 * @param product
	 * @return
	 */
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String get() {

		return "index";

	}
}
