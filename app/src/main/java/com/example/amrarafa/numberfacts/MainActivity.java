package com.example.amrarafa.numberfacts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MyDialogFragment.Callback {

    private TextView factText;
    private RelativeLayout relativeLayout;
    private ColorWheel colorwheel =new ColorWheel();
    private Spinner daysSpinner;
    private Spinner monthsSpinner;
    private EditText numberInput;
    private Button showFactbutton;
    private Button anotherFactButton;
    private boolean isItDate;
    private int dayNumber;
    private int monthNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        factText=(TextView)findViewById(R.id.facttextView1);
        relativeLayout= (RelativeLayout) findViewById(R.id.relativelayout);
        daysSpinner= (Spinner) findViewById(R.id.spinner_day);
        monthsSpinner= (Spinner) findViewById(R.id.spinner_month);
        numberInput= (EditText) findViewById(R.id.number_fact);
        showFactbutton=(Button)findViewById(R.id.show_fact_button);
        anotherFactButton=(Button)findViewById(R.id.another_fact_button);

        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_number, android.R.layout.simple_spinner_item);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(daysAdapter);

        final ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.month_number, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(monthAdapter);

        daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG",String.valueOf(i));
                dayNumber=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG",String.valueOf(i));
                monthNumber=i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    @Override
    protected void onStart() {

        MyDialogFragment myDialog= new MyDialogFragment();
        myDialog.show(getFragmentManager(), "MyDialog");
        super.onStart();
    }


    void fetchNumberUrl(final String choosenNumber){

        String url="http://numbersapi.com/"+choosenNumber+"?json";

        if(!isNetworkAvailable()){
            factText.setText("No internet connection");
            return;
        }

        RequestQueue requestQueue;

        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String text=response.getString("text");
                            factText.setText(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("TAG",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        factText.setText(error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    void fetchDateUrl(final int month,final int day){

        String url="http://numbersapi.com/"+month+"/"+day+"?json";

        if(!isNetworkAvailable()){
            factText.setText("No internet connection");
            return;
        }

        RequestQueue requestQueue;

        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String text=response.getString("text");
                            factText.setText(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("TAG",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        factText.setText(error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    public void onChangingType(View v){
        daysSpinner.setVisibility(View.INVISIBLE);
        monthsSpinner.setVisibility(View.INVISIBLE);
        numberInput.setVisibility(View.INVISIBLE);
        factText.setText("");
        MyDialogFragment myDialog= new MyDialogFragment();
        myDialog.show(getFragmentManager(), "MyDialog");
    }

    public void showFactListener(View v){
        anotherFactButton.setVisibility(View.VISIBLE);
        showFactbutton.setVisibility(View.INVISIBLE);
        daysSpinner.setVisibility(View.INVISIBLE);
        monthsSpinner.setVisibility(View.INVISIBLE);
        numberInput.setVisibility(View.INVISIBLE);

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(numberInput.getWindowToken(), 0);



        if(isItDate==false){

           fetchNumberUrl(numberInput.getText().toString());

        }
        else {
            fetchDateUrl(monthNumber+1,dayNumber+1);

        }






    }
    @Override
    public void onNumberSelected(int choosenNumber) {
        Toast.makeText(this,Integer.toString(choosenNumber), Toast.LENGTH_SHORT)
                .show();
        int color= colorwheel.getcolor();
        relativeLayout.setBackgroundColor(color);
        anotherFactButton.setVisibility(View.INVISIBLE);
        showFactbutton.setVisibility(View.VISIBLE);


        if(choosenNumber==0){

            numberInput.setVisibility(View.VISIBLE);
            isItDate=false;
        }
        else {
            daysSpinner.setVisibility(View.VISIBLE);
            monthsSpinner.setVisibility(View.VISIBLE);
            isItDate=true;
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
