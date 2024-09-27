package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator("sample.json");
        // Translator translator = new InLabByHandTranslator();
        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        String q = "quit";
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();
        while (true) {
            String country = promptForCountry(translator);
            if (q.equals(country)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            // country = countryConverter.fromCountry(country);

            String language = promptForLanguage(translator, countryConverter.fromCountry(country));
            if (language.equals(q)) {
                break;
            }
            System.out.println(country + " in " + language + " is " + translator.translate(countryConverter
                    .fromCountry(country).toLowerCase(), languageConverter.fromLanguage(language)));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (q.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        CountryCodeConverter converter = new CountryCodeConverter();
        List<String> countries = translator.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            String translation = converter.fromCountryCode(countries.get(i));
            countries.set(i, translation);
        }

        Collections.sort(countries);
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i));
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        List<String> languages = translator.getCountryLanguages(country);
        for (int i = 0; i < languages.size(); i++) {
            String translation = converter.fromLanguageCode(languages.get(i));
            languages.set(i, translation);
        }
        Collections.sort(languages);
        for (int i = 0; i < languages.size(); i++) {
            System.out.println(languages.get(i));
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
