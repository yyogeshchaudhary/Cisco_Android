package cisco.services.xchange.utils;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import cisco.services.xchange.AppDefine;

/**
 * Created by yogi on 14/03/16.
 */
public class NetworkUtil {

    public static final int ERROR = 3;
    public static final int SUCCESS = 4;
    public static final int RESULT_OK = 0;
    public static final int RESULT_FAIL = 1;
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';

    public String getPostResponse(String urlString, HashMap<String, Object> inputMap) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(AppDefine.NETWORK_CONN_TIMEOUT);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        String postParameters = createQueryStringFormParameters(inputMap);
        if (postParameters != null) {
            conn.setFixedLengthStreamingMode(postParameters.getBytes().length);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(postParameters);
            out.close();
        }
        int responseCode = conn.getResponseCode();
        String responseMsg = null;
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return getResponseText(in);
        }
        return responseMsg;
    }

    public String getJsonResponse(String urlString, String authorization, HashMap<String, Object> inputMap) throws Exception{
        String responseMsg = null;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(AppDefine.NETWORK_CONN_TIMEOUT);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (authorization != null)
            conn.setRequestProperty("Authorization", authorization);
        conn.setDoOutput(true);
        JSONObject postParameters = createQueryJsonFormParameters(inputMap);
        if (postParameters != null) {
            conn.setFixedLengthStreamingMode(postParameters.toString().getBytes().length);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(postParameters);
            out.close();
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return getResponseText(in);
        }

        return responseMsg;
    }

    private String getResponseText(InputStream in) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(in, writer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String createQueryStringFormParameters(Map<String, Object> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode((String)
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return parametersAsQueryString.toString();
    }

    public JSONObject createQueryJsonFormParameters(HashMap<String, Object> parameters) throws JSONException {
        JSONObject json = new JSONObject();
        for (String parameterName : parameters.keySet()) {
            if (parameters.get(parameterName) instanceof HashMap)
                json.put(parameterName, createJsonStringFromParameters((HashMap<String, Object>) parameters.get(parameterName)).toString());
            else
                json.put(parameterName, parameters.get(parameterName));
        }
        return json;
    }

    public JSONObject createJsonStringFromParameters(HashMap<String, Object> parameters) throws JSONException {
        JSONObject json = new JSONObject();
        for (String parameterName : parameters.keySet()) {
            if (parameters.get(parameterName) instanceof HashMap)
                json.put(parameterName, createJsonStringFromParameters((HashMap<String, Object>) parameters.get(parameterName)));
            else
                json.put(parameterName, parameters.get(parameterName));
        }
        return json;
    }
}
