package com.ailk.sets.platform.intf.model.wx.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(InputStream is, Class<T> cls) {
		Object result = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(cls);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			result = jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return (T) result;
	}

	public static String marshal(Object root, Class<?> className) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(className);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(root, out);
			String result = out.toString();
			return result.replace("&lt;![CDATA[", "<![CDATA[").replace("]]&gt;", "]]>");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
