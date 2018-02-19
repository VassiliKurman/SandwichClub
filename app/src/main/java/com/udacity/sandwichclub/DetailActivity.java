package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Vassili Kurman
 * Date: 19/02/2018
 * Version 1.1
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv) ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * For the sandwich provided here text view is found by id and sets
     * values to text views retrieved from sandwich.
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        if(sandwich == null)
            return;

        // Getting text views
        final TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        final TextView tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        final TextView tvDescription = findViewById(R.id.description_tv);
        final TextView tvIngredients = findViewById(R.id.ingredients_tv);

        // Getting arrays as strings to remove square brackets
        String arrNames = sandwich.getAlsoKnownAs().toString();
        String arrIngredients = sandwich.getIngredients().toString();

        // Setting text to text views
        tvAlsoKnownAs.setText(arrNames.substring(1, arrNames.length() - 1));
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        tvDescription.setText(sandwich.getDescription());
        tvIngredients.setText(arrIngredients.substring(1, arrIngredients.length() - 1));
    }
}
