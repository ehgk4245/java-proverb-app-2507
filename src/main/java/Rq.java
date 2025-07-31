import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Rq {

    private final Map<String, String> paramsMap;
    private final String actionName;

    public Rq(String cmd) {
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";
        
        String[] queryStringBits = queryString.split("&");

        paramsMap = Arrays.stream(queryString.split("&"))
                .map(part -> part.split("=", 2))
                .filter(bits -> bits.length > 0 && !bits[0].trim().isEmpty() && !bits[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        bits -> bits[0].trim(),
                        bits -> bits[1].trim()
                ));
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
