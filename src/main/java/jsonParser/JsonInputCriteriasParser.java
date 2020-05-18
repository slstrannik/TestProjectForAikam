package jsonParser;

import exception.ResultException;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public List<CriteriaClass> process() throws ResultException {
        if (inputFileName == null || inputFileName.isEmpty()) throw new ResultException("Input file name is null or empty");
        FileInputStream fileInputStream = null;
        JsonReader jsonReader = null;
        List<CriteriaClass> criteriaClassList = new ArrayList<>();
        CriteriaClass criteriaClass = null;
        try {
            fileInputStream = new FileInputStream(inputFileName);
            jsonReader = Json.createReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            JsonObject root = jsonReader.readObject();
            if (root.isEmpty()) throw new ResultException("Input json is empty");;
            JsonValue value = root.get("criterias");
            if (value != null && value.getValueType() == JsonValue.ValueType.ARRAY) {
                for (JsonValue jsonValue : value.asJsonArray()) {
                    criteriaClass = fillCriteriaClass(jsonValue);
                }
            } else {
                criteriaClass = fillCriteriaClassDateInterval(root);
            }
            if (criteriaClass != null) criteriaClassList.add(criteriaClass);
            else throw new ResultException("Criteries not found in json");
            return  criteriaClassList;
        } catch (FileNotFoundException e) {
            throw new ResultException("Could not open input file: " + inputFileName);
        } catch (NumberFormatException | ParseException e) {
            throw new ResultException("Error type in json input data");
        } catch (JsonException e) {
            throw new ResultException("Error parse of json input file");
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
    }

    private CriteriaClass fillCriteriaClass(JsonValue jsonValue) {
        if (jsonValue.getValueType() != JsonValue.ValueType.OBJECT) return null;
        JsonObject criteriaObject = jsonValue.asJsonObject();
        CriteriaClass criteriaClass = new CriteriaClass();

        if (criteriaObject.containsKey("lastName")) {
            criteriaClass.setLastName(criteriaObject.getString("lastName"));
        }
        if (criteriaObject.containsKey("productName")) {
            criteriaClass.setLastName(criteriaObject.getString("productName"));
        }
        if (criteriaObject.containsKey("minTimes")) {
            criteriaClass.setMinTimes(criteriaObject.getInt("minTimes"));
        }
        if (criteriaObject.containsKey("minExpenses")) {
            criteriaClass.setMinExpenses(criteriaObject.getJsonNumber("minExpenses").doubleValue());
        }
        if (criteriaObject.containsKey("maxExpenses")) {
            criteriaClass.setMaxExpenses(criteriaObject.getJsonNumber("maxExpenses").doubleValue());
        }
        if (criteriaObject.containsKey("badCustomers")) {
            criteriaClass.setBadCustomers(criteriaObject.getInt("badCustomers"));
        }
        return criteriaClass.isEmpty() ? null : criteriaClass;
    }

    private CriteriaClass fillCriteriaClassDateInterval(JsonValue jsonValue) throws ParseException {
        if (jsonValue.getValueType() != JsonValue.ValueType.OBJECT) return null;
        JsonObject criteriaObject = jsonValue.asJsonObject();
        CriteriaClass criteriaClass = new CriteriaClass();

        if (criteriaObject.containsKey("startDate")) {
            criteriaClass.setStartDate(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(criteriaObject.getString("startDate")));
        }
        if (criteriaObject.containsKey("endDate")) {
            criteriaClass.setEndDate(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(criteriaObject.getString("endDate")));
        }
        return criteriaClass.isEmpty() ? null : criteriaClass;
    }
}