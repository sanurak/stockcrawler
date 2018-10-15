package miki.assignment.stockcrawler.lambda.function;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import miki.assignment.stockcrawler.restclient.AbstractRequest;
import miki.assignment.stockcrawler.restclient.AbstractResponse;
import miki.assignment.stockcrawler.restclient.Response;
import miki.assignment.stockcrawler.restclient.RestClient;
import miki.assignment.stockcrawler.util.JsonUtil;

public class IndexCrawlerRequestHandler implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		String setIndexUrl = System.getenv("setIndexUrl");
		String restIndexUrl = System.getenv("restIndexUrl");
		String restUsername = System.getenv("restUsername");
		String restPassword = System.getenv("restPassword");
		context.getLogger().log("setIndexUrl: " + setIndexUrl);
		context.getLogger().log("restIndexUrl: " + restIndexUrl);

		Response<String> response = new AbstractResponse<String>();

		try {
			DecimalFormat df = new DecimalFormat("#,###.00");

			Document document = Jsoup.connect(setIndexUrl).get();

			List<Map<String, String>> setMapList = new ArrayList<Map<String, String>>();

			for (Element row : document.select("div.table-responsive table.table-info tbody tr")) {
				Elements columns = row.select("td");

				if (columns.size() >= 2) {
					String setName = columns.get(0).text();
					String setValue = columns.get(1).text();

					Number formattedValue = df.parse(setValue);

					Map<String, String> setMap = new HashMap<String, String>();
					setMap.put("setName", setName);
					setMap.put("setValue", formattedValue.toString());

					setMapList.add(setMap);

				}
			}

			AbstractRequest<List<Map<String, String>>> request = new AbstractRequest<List<Map<String, String>>>();
			request.setUsername(restUsername);
			request.setPassword(restPassword);
			request.setRequestObject(setMapList);

			RestClient restClient = new RestClient();
			response = restClient.postJsonData(restIndexUrl, request);

		} catch (IOException e) {
			response.setStatus(0);
			response.setMessage("Error Parsing Set Index:" + e.getMessage());

			context.getLogger().log(e.getMessage());

		} catch (ParseException e) {
			response.setStatus(0);
			response.setMessage("Error Convert Set Value:" + e.getMessage());

			context.getLogger().log(e.getMessage());
		}

		return JsonUtil.convertToJson(response);

	}

}
