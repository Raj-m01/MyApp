package com.example.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText searchBar;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        button = findViewById(R.id.submit_btn);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(view -> {

            String enteredName = searchBar.getText().toString().trim();

            if (enteredName.length() == 0) {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "https://api.nationalize.io/?name=" + enteredName;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                    response -> {

                        try {

                            JSONArray countryDataArray = response.getJSONArray("country");
                            int size = countryDataArray.length();

                            ArrayList<DataModel> list = new ArrayList<>();
                            CustomAdapter adapter = new CustomAdapter(list);

                            for (int i = 0; i < size; i++) {
                                JSONObject obj = countryDataArray.getJSONObject(i);
                                list.add(new DataModel(obj.getString("country_id")
                                        , obj.getString("probability")));
                            }

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    },
                    error -> {
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonObjectRequest);

        });

    }
}