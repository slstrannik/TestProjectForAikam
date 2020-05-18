package jsonParser;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveJsonOutputDataClass {
    JsonOutputBuilder jsonOutputBuilder;
    String outputFileName;

    public SaveJsonOutputDataClass(String fileName, JsonOutputBuilder jsonOutputBuilder) {
        this.jsonOutputBuilder = jsonOutputBuilder;
        this.outputFileName = fileName;
    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
            JsonWriter jsonWriter = Json.createWriter(fileOutputStream);
            jsonWriter.write(jsonOutputBuilder.build());
            fileOutputStream.close();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
