package com.ailk.sets.platform.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PFUtils {
	public static <T> String getIdString(List<T> ids) {
		StringBuilder result = new StringBuilder("");
		if (!CollectionUtils.isEmpty(ids)) {
			for (T t : ids)
				result.append(t + ",");
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public static String toJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		Writer w = new StringWriter();
		String json = null;
		om.writeValue(w, obj);
		json = w.toString();
		w.close();
		return json;
	}
}
