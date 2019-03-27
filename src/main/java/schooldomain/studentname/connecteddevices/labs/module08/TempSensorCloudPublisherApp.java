/**
 * @authon : Navin Raman
 */

package schooldomain.studentname.connecteddevices.labs.module08;

import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URLEncodedUtils;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * This class is used to send via http
 */
public class TempSensorCloudPublisherApp {
	
	// Final static variables 
	public static final String DEFAULT_BASE_URL = "https://things.ubidots.com/api/v1.6/";

	// Instance variables
	private String token;
	private String apiKey;
	private String baseUrl;

	private Map<String, String> tokenHeader;
	private Map<String, String> apiKeyHeader;

	public static TempSensorCloudPublisherApp TSCPA;

	/**
	 * Constructor used to create object 
	 * 
	 * @param apiKey
	 */
	TempSensorCloudPublisherApp(String apiKey) {
		this(apiKey, DEFAULT_BASE_URL);
	}

	/**
	 * Constructor used to create object with different parameters 
	 * 
	 * @param apiKey: key
	 * @param baseUrl: ubidots endpoint url
	 */
	TempSensorCloudPublisherApp(String apiKey, String baseUrl) {
		this.baseUrl = baseUrl;
		this.apiKey = apiKey;
		token = null;

		apiKeyHeader = new HashMap<>();
		apiKeyHeader.put("X-UBIDOTS-APIKEY", this.apiKey);

		initialize();
	}

	/**
	 * Constructor used to create object with different parameters
	 * 
	 * @param token: token
	 * @param isToken: boolean
	 */
	TempSensorCloudPublisherApp(String token, boolean isToken) {
		this.token = token;
		baseUrl = DEFAULT_BASE_URL;
		apiKey = null;

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	/**
	 * Constructor used to create object with different parameters
	 * 
	 * @param token
	 * @param isToken
	 * @param baseUrl
	 */
	TempSensorCloudPublisherApp(String token, boolean isToken, String baseUrl) {
		this.token = token;
		this.baseUrl = baseUrl;
		apiKey = null;

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	/**
	 * Method to initialize
	 */
	public void initialize() {
		recieveToken();
	}

	/**
	 * method to set base url
	 * 
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * method to receive token
	 */
	private void recieveToken() {
		Gson gson = new Gson();
		token = (String) gson.fromJson(postWithApiKey("auth/token"), Map.class).get("token");

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	/**
	 * Method to post using API key
	 * 
	 * @param path
	 * @return response
	 */
	private String postWithApiKey(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(apiKeyHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpPost post = new HttpPost(url);

			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name));
			}

			HttpResponse resp = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Method to get custom headers
	 * 
	 * @return customHeaders
	 */
	private Map<String, String> getCustomHeaders() {
		Map<String, String> customHeaders = new HashMap<>();

		customHeaders.put("content-type", "application/json");

		return customHeaders;
	}

	/**
	 * Method to prepare headers
	 * 
	 * @param arg
	 * @return headers
	 */
	private Map<String, String> prepareHeaders(Map<String, String> arg) {
		Map<String, String> headers = new HashMap<>();

		headers.putAll(getCustomHeaders());
		headers.putAll(arg);

		return headers;
	}

	/**
	 * Method is used to maintain compatibility with previous version Perform
	 * a GET request on the API with a given path.
	 * 
	 * @param path Path to append to the base URL
	 * @return Response from the API. The API sends back data encoded in JSON. In
	 *         the event of an error, will return null.
	 */
	String get(String path) {
		return get(path, null);
	}

	/**
	 * Method is user to perform a GET request on the API with a given path
	 * 
	 * @param path: Path to append to the base URL.
	 * @param customParams: Custom parameters added by the user
	 * @return Response from the API. 
	 * 
	 * The API sends back data encoded in JSON. In the event of an error,
	 * will return null.
	 */
	String get(String path, Map<String, String> customParams) {
		
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		if (customParams != null) {
			List<NameValuePair> params = new LinkedList<>();

			for (Map.Entry<String, String> entry : customParams.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

			}
			path.concat("?");
			path.concat(URLEncodedUtils.format(params, "utf8"));
		}

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpGet get = new HttpGet(url);

			for (String name : headers.keySet()) {
				get.setHeader(name, headers.get(name));
			}

			HttpResponse resp = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();

			// needed the result field
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(response).getAsJsonObject();
			if (jsonObject.has("results")) {
				response = jsonObject.get("results").toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * This method is used to perform a POST request on the API with a given
	 * path
	 * 
	 * @param path Path to append to the base URL.
	 * @param json String of data encoded in JSON to send to the server.
	 * @return Response from the API.
	 * 
	 * The API sends back data encoded in JSON. In the event of an error,
	 * will return null.
	 */
	String post(String path, String json) {
		
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpPost post = new HttpPost(url);

			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name));
			}

			post.setEntity(new StringEntity(json));
			HttpResponse resp = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * This method is used to perform a DELETE request on the API with
	 * a given path
	 * 
	 * @param path Path to append to the base URL.
	 * @return Response from the API.
	 * 
	 * The API sends back data encoded in JSON. In the event of an error,
	 * will return null.
	 */
	String delete(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpDelete delete = new HttpDelete(url);

			for (String name : headers.keySet()) {
				delete.setHeader(name, headers.get(name));
			}

			HttpResponse resp = client.execute(delete);

			if (resp.getEntity() == null) {
				response = "";
			} else {
				BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line;

				while ((line = rd.readLine()) != null) {
					result.append(line);
				}

				response = result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	/**
	 * Method to generate a random value
	 * 
	 * @param minimum
	 * @param maximum
	 * @return String random value
	 */
	public String generateRandomvalue(float minimum, float maximum) {
		Random r = new Random();
		float random = minimum + r.nextFloat() * (maximum - minimum);
		System.out.println("\nTemperature Value : " + Float.toString(random));
		return Float.toString(random);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final String UBIDOTS_PATH_SEPERATOR = "/";
		final String UBIDOTS_VARIABLE_LABEL = "temperature";
		final String UBIDOTS_DEVICE_LABEL = "demo";
		final String UBIDOTS_TOPIC_DEFAULT = "devices" + UBIDOTS_PATH_SEPERATOR + UBIDOTS_DEVICE_LABEL;
		String _authToken = "A1E-yvddAEn7t9o8qfPVDV6auJywPyoFcZ";
		float minimum = 0f;
		float maximum = 45f;

		try {
			TSCPA = new TempSensorCloudPublisherApp(_authToken, true);
			JSONObject Jobj = new JSONObject();
			while (true) {
				Jobj.put(UBIDOTS_VARIABLE_LABEL, TSCPA.generateRandomvalue(minimum, maximum));
				String json = Jobj.toString();
				TSCPA.post(UBIDOTS_TOPIC_DEFAULT, json);
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}