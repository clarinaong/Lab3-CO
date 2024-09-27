package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private Map<String, ArrayList> codetoLanguages = new HashMap<String, ArrayList>();
    private Map<String, Map<String, String>> codestoTranslations = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                String countryCodeKey = "alpha3";
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                codestoTranslations.put(jsonObject.get(countryCodeKey).toString(), new HashMap<>());
                codetoLanguages.put(jsonObject.getString(countryCodeKey), new ArrayList<>());
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!"id".equals(key) && !"alpha2".equals(key) && !countryCodeKey.equals(key)) {
                        codetoLanguages.get(jsonObject.getString(countryCodeKey)).add(key);
                        codestoTranslations.get(jsonObject.getString(countryCodeKey)).put(key, jsonObject
                                .getString(key));
                    }
                }

            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        if (codetoLanguages.containsKey(country.toLowerCase())) {
            return codetoLanguages.get(country.toLowerCase());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        if (codetoLanguages.size() != 0) {
            List<String> countries = new ArrayList<>();
            for (int i = 0; i < codetoLanguages.size(); i++) {
                countries.add(codetoLanguages.keySet().toArray()[i].toString());
            }
            return countries;
        }
        return new ArrayList<>();
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        if (codestoTranslations.containsKey(country)) {
            if (codestoTranslations.get(country).containsKey(language)) {
                return codestoTranslations.get(country).get(language);
            }
        }
        return null;
    }
}
