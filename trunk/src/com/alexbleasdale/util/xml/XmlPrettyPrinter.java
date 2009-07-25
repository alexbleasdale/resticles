package com.alexbleasdale.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;

import org.apache.commons.lang.StringUtils;
import org.apache.xml.serialize.XMLSerializer;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

public class XmlPrettyPrinter {

	/**
	 * Uses Xerces - method works but is now deprecated...
	 * 
	 * @param doc
	 * @param out
	 * @throws Exception
	 */
	public void xercesXmlPrettyPrint(Document doc, OutputStream out)
			throws Exception {
		org.apache.xml.serialize.OutputFormat format = new org.apache.xml.serialize.OutputFormat(
				doc);
		format.setLineWidth(65);
		format.setIndenting(true);
		format.setIndent(2);
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(doc);
	}

	/**
	 * Uses XOM to create a pretty printed XML Document
	 * 
	 * @param xml
	 * @return
	 * @throws ParsingException
	 * @throws IOException
	 */
	public static String xomXmlPrettyPrint(String xml) throws ParsingException,
			IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Serializer serializer = new Serializer(out);
		serializer.setIndent(4); // or whatever you like
		serializer.write(new Builder().build(xml, ""));
		return out.toString("UTF-8");
	}

	/**
	 * Uses DOM4J to pretty print the XML document
	 * 
	 * @param xml
	 * @return
	 */
	public String dom4jXmlPrettyPrint(final String xml) {

		if (StringUtils.isBlank(xml)) {
			throw new RuntimeException("xml was null or blank in prettyPrint()");
		}

		final StringWriter sw;

		try {
			final OutputFormat format = OutputFormat.createPrettyPrint();
			final org.dom4j.Document document = DocumentHelper.parseText(xml);
			sw = new StringWriter();
			final XMLWriter writer = new XMLWriter(sw, format);
			writer.write(document);
		} catch (Exception e) {
			throw new RuntimeException("Error pretty printing xml:\n" + xml, e);
		}
		return sw.toString();
	}

	/**
	 * Uses XSLT to pretty print an XML Document
	 * 
	 * @param doc
	 * @param out
	 * @throws Exception
	 */
	public void xsltXmlPrettyPrinter(Document doc, OutputStream out)
			throws Exception {

		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer serializer;
		try {
			serializer = tfactory.newTransformer();
			// Setup indenting to "pretty print"
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");

			serializer.transform(new DOMSource(doc), new StreamResult(out));
		} catch (TransformerException e) {
			// this is fatal, just dump the stack and throw a runtime exception
			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

}
