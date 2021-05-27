package nullblade.craftanddeath.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    public static String formatString(String string) {
        return string.replace("$(PC)", "§3").replace("$(SC)", "§b").replace('&', '\u00a7');
    }


    public static Set<String> setFromJson(String json) {
        return json == null ? new HashSet<>() : new Gson().fromJson(json, new TypeToken<Set<String>>() {}.getType());
    }

    public static String addToJsonSet(String json, String element) {
        Set<String> set = setFromJson(json);

        set.add(element);
        return new Gson().toJson(set);
    }


    public static List<String> smartAutocomplete(List<String> variants, String[] args) {
        List<String> autocomplete = new ArrayList<>();

        for (String variant : variants) {
            if (variant.startsWith(args[args.length - 1])) {
                autocomplete.add(variant);
            }
        }

        return autocomplete;
    }
}
