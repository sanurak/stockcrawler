package miki.assignment.stockcrawler.restclient;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.reflect.TypeToken;

public class RestClient {
	public static final int SUCCESS = 1;

	private HttpRequestFactory createRequestFactory() {
		HttpTransport transport = new NetHttpTransport();
		HttpRequestFactory requestFactory = transport.createRequestFactory();

		return requestFactory;
	}

	public <I, O> Response<O> postJsonData(String url, I inputObject) throws IOException {
		JacksonFactory jsonFactory = new JacksonFactory();
		JsonObjectParser jsonObjectParser = new JsonObjectParser(jsonFactory);

		System.out.println("url:" + url);
		System.out.println("json:" + jsonFactory.toPrettyString(inputObject));

		GenericUrl gnerateUrl = new GenericUrl(url);
		HttpContent content = new JsonHttpContent(jsonFactory, inputObject);

		HttpRequest httpRequest = createRequestFactory().buildPostRequest(gnerateUrl, content);

		// Set the parser to use for parsing the returned JSON data
		httpRequest.setParser(jsonObjectParser);

		// Use GSON's TypeToken to let the parser know to expect a
		// List<GithubUser>
		Type type = new TypeToken<AbstractResponse<O>>() {
		}.getType();
		@SuppressWarnings("unchecked")
		Response<O> response = (Response<O>) httpRequest.execute().parseAs(type);

		return response;

	}

}
