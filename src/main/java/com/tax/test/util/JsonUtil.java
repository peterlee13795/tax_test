package com.tax.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


	public static String objectToJson(Object json) {
		try {
			return OBJECT_MAPPER.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T jsonToObject(String json, Class<T> target) {
		try {
			return OBJECT_MAPPER.readValue(json, target);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> jsonToList(String json, Class<T> target) {
		try {
			return OBJECT_MAPPER.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, target));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
