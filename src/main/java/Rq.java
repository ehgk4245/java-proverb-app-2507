import java.util.HashMap;
import java.util.Map;

public class Rq {

    private final Map<String, String> paramsMap;
    private final String actionName;

    public Rq(String cmd) {
        paramsMap = new HashMap<>();
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";
        
        String[] queryStringBits = queryString.split("&");
        
        for (String queryStringBit : queryStringBits) {
            String[] queryParamBits = queryStringBit.split("=", 2);
            String key = queryParamBits[0].trim();
            String value = queryParamBits.length > 1 ? queryParamBits[1].trim() : "";

            if (value.isEmpty()) {
                continue;
            }

            paramsMap.put(key, value);
        }
    }

    public String getActionName() {
        return actionName;
    }

    public String getParam(String paramName, String defaultValue) {
        if (paramsMap.containsKey(paramName)) return paramsMap.get(paramName);
        return defaultValue;
    }

    public long getParamAsInt(String paramName, int defaultValue) {
        if (paramsMap.containsKey(paramName)) {
            try {
                return Integer.parseInt(paramsMap.get(paramName));
            }
            catch (NumberFormatException e) {
                return -1;
            }
        }
        return defaultValue;
    }
}
