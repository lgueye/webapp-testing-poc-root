/**
 * 
 */
package org.diveintojee.poc.webapp.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author louis.gueye@gmail.com
 * 
 */
public class FunctionnalTestsFixtures {

	public static void assertCorrectPageTitleIsDisplayed(
			String documentAsString, String expectedValue) throws Throwable {
		String path = "/html/head/title";
		assertDocumentContainsValueAtPath(documentAsString, path, expectedValue);
	}

	public static void assertDocumentContainsNodeAtPath(
			String documentAsString, String path) throws Throwable {

		assertNotNull(documentAsString);

		assertNotNull(path);

		// System.out.println(documentAsString);

		Document doc = FunctionnalTestsFixtures.getDocumentBuilderInstance()
				.parse(new ByteArrayInputStream(documentAsString.getBytes()));

		assertNotNull(doc);

		XPathExpression expr = FunctionnalTestsFixtures.getXPathInstance()
				.compile(path);

		assertNotNull(expr);

		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		assertNotNull(nodes);

		assertEquals(1, nodes.getLength());

		Node node = nodes.item(0);

		assertNotNull(node);

	}

	public static void assertDocumentContainsValueAtPath(
			String documentAsString, String path, String expectedValue)
			throws Throwable {

		assertNotNull(documentAsString);

		assertNotNull(path);

		assertNotNull(expectedValue);

		// System.out.println(documentAsString);

		Document doc = FunctionnalTestsFixtures.getDocumentBuilderInstance()
				.parse(new ByteArrayInputStream(documentAsString.getBytes()));

		assertNotNull(doc);

		XPathExpression expr = FunctionnalTestsFixtures.getXPathInstance()
				.compile(path);

		assertNotNull(expr);

		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		assertNotNull(nodes);

		assertEquals(1, nodes.getLength());

		Node node = nodes.item(0);

		assertNotNull(node);

		// new XMLSerializer(System.out, new OutputFormat(doc)).serialize(node);

		String nodeText = node.getTextContent();

		assertNotNull(nodeText);

		assertEquals(expectedValue, nodeText.trim());

	}

	public static DocumentBuilder getDocumentBuilderInstance() throws Throwable {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();

		return domFactory.newDocumentBuilder();

	}

	public static XPath getXPathInstance() {

		XPathFactory xpathFactory = XPathFactory.newInstance();

		return xpathFactory.newXPath();

	}
}
