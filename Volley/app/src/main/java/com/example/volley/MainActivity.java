package com.example.volley;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    String input;
    EditText input_text;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view_result);
        input_text = (EditText) findViewById(R.id.input_text) ;
        Button buttonParse = findViewById(R.id.button_parse);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = input_text.getText().toString();
                jsonParse();
            }
        });
    }
        private void jsonParse()
        {
                initVolleyCallback();
                mVolleyService = new VolleyService(mResultCallback, this);
                mVolleyService.getDataVolley("GETCALL", "http://192.168.0.106:5000/get/data");
                JSONObject sendObj = null;

                try {
                    String hello = '{' + "'Test'"  + ":'" + input + "'}";
                    Log.d(TAG,hello);
                    sendObj = new JSONObject(hello);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mVolleyService.postDataVolley("POSTCALL", "http://192.168.0.106:5000/post/data", sendObj);
            }
            void initVolleyCallback()
                {
                    mResultCallback = new IResult()
                    {
                        @Override
                        public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                            Log.d(TAG, "Volley requester " + requestType);
                            Log.d(TAG, "Volley JSON post" + response);
                            JSONArray wArray = response.getJSONArray("employees");
                            JSONObject object=wArray.getJSONObject(Integer.parseInt(String.valueOf(0)));
                            String mainWeather=object.getString("result");
                            textView.setText(mainWeather);
                        }

                        @Override
                        public void notifyError(String requestType, VolleyError error) {
                            Log.d(TAG, "Volley requester " + requestType);
                            Log.d(TAG, "Volley JSON post" + "That didn't work!");
                            textView.setText("That didn't work!");
                        }
                    };
                }
        }


