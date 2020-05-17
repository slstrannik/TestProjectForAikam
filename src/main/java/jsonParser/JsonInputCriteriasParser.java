package jsonParser;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonInputCriteriasParser {
    String inputFileName;

    public JsonInputCriteriasParser(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public List<CriteriaClass> process() {
        if (inputFileName == null || inputFileName.isEmpty()) return null;
        FileInputStream fileInputStream = null;
        JsonReader jsonReader = null;
        List<CriteriaClass> criteriaClassList = new ArrayList<>();
        CriteriaClass criteriaClass = null;
        try {
            fileInputStream = new FileInputStream(inputFileName);
            jsonReader = Json.createReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            JsonObject root = jsonReader.readObject();
            JsonValue value = root.getOrDefault("criterias", null);
            if (value != null) {
                for (JsonValue jsonValue : root.getJsonArray("criterias")) {
                    criteriaClass = fillCriteriaClass(jsonValue);
                    if (criteriaClass != null) criteriaClassList.add(criteriaClass);
                }
            } else {
                criteriaClass = fillCriteriaClass(root);
                if (criteriaClass != null) criteriaClassList.add(criteriaClass);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not open input file: " + inputFileName);
        } catch (NumberFormatException | ParseException e) {
            System.out.println("Error type in json input data");
        } catch (JsonException e) {
            System.out.println("Error parse of json input file");
        }
            finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                    if (jsonReader != null) jsonReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  criteriaClassList;
    }

    private CriteriaClass fillCriteriaClass(JsonValue jsonValue) throws ParseException {
        CriteriaClass criteriaClass = new CriteriaClass();
        JsonObject criteriaObject = jsonValue.asJsonObject();
        String value;

        value = criteriaObject.getString("lastName", null);
        criteriaClass.setLastName(value);

        value = criteriaObject.getString("productName", null);
        criteriaClass.setProductName(value);


        if (criteriaObject.containsKey("minTimes")) {
            criteriaClass.setMinTimes(criteriaObject.getInt("minTimes"));
        }

        if (criteriaObject.containsKey("minExpenses")) {
            criteriaClass.setMinExpenses(criteriaObject.getJsonNumber("minExpenses").doubleValue());
        }

        if (criteriaObject.containsKey("maxExpenses")) {
            criteriaClass.setMaxExpenses(criteriaObject.getJsonNumber("maxExpenses").doubleValue());
        }

        value = criteriaObject.getString("startDate", null);
        if (value != null) criteriaClass.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));

        value = criteriaObject.getString("endDate", null);
        if (value != null) criteriaClass.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(value));

        if (criteriaObject.containsKey("badCustomers")) {
            criteriaClass.setBadCustomers(criteriaObject.getInt("badCustomers"));
        }
            return criteriaClass.isEmpty() ? null : criteriaClass;
    }
}