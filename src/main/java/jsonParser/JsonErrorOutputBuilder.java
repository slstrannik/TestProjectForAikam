package jsonParser;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class JsonErrorOutputBuilder implements JsonOutputBuilder {
    String message;

    public JsonErrorOutputBuilder(String message) {
        this.message = message;
    }

    @Override
    public JsonValue build() {
        JsonObjectBuilder rootObjectBuilder = Json.createObjectBuilder();
        rootObjectBuilder.add("type", "error");
        rootObjectBuilder.add("message", message);
        return rootObjectBuilder.build();
    }
}
