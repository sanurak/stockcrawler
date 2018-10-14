package miki.assignment.stockcrawler.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	public static <T> String convertToJson(T t) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();

		Gson gson = builder.create();
		String jsonString = gson.toJson(t);

		return jsonString;
	}

	public static <T> T convertFromJson(String jsonString, Class<T> tClass) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();

		Gson gson = builder.create();
		T t = gson.fromJson(jsonString, tClass);

		return t;
	}

}
