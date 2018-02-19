package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vassili Kurman
 * Date: 19/02/2018
 * Version 1.1
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        try {
            // Parsing json string sandwich to json sandwich object
            JSONObject jsonObject = new JSONObject(json);
            // Getting json names object from json sandwich object
            JSONObject names = jsonObject.getJSONObject("name");

            JSONArray namesArray = names.optJSONArray("alsoKnownAs");
            JSONArray ingredientsArray = jsonObject.optJSONArray("ingredients");

            // Getting individual values from json object
            final String mainName = names.optString("mainName");
            final String placeOfOrigin = jsonObject.optString("placeOfOrigin");
            final String description = jsonObject.optString("description");
            final String image = jsonObject.optString("image");

            // Getting names array values
            final List<String> alsoKnownAs = new ArrayList<>();
            if(namesArray.length() > 0) {
                for(int i = 0; i < namesArray.length(); i++) {
                    alsoKnownAs.add(namesArray.optString(i));
                }
            }
            // Getting ingredients array values
            final List<String> ingredients = new ArrayList<>();
            if(ingredientsArray.length() > 0) {
                for(int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.optString(i));
                }
            }

            sandwich = new Sandwich(
                    mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: ", e);
        }

        return sandwich;
    }
}
