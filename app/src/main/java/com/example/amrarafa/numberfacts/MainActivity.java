package com.example.amrarafa.numberfacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        factText=(TextView)findViewById(R.id.facttextView1);
        relativeLayout= (RelativeLayout) findViewById(R.id.relativelayout);


    }

    @Override
    protected void onStart() {

        MyDialogFragment myDialog= new MyDialogFragment();
        myDialog.show(getFragmentManager(), "MyDialog");
        super.onStart();
    }

    @Override
    public void onNumberSelected(String choosenNumber) {
        Toast.makeText(this,choosenNumber, Toast.LENGTH_SHORT)
                .show();
        int color= colorwheel.getcolor();
        relativeLayout.setBackgroundColor(color);
        fetchUrl(choosenNumber);
    }

    void fetchUrl(final String choosenNumber){

        String url="http://numbersapi.com/"+choosenNumber+"?json";

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
                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);

    }

    public void onChangingNumber(View v){
        MyDialogFragment myDialog= new MyDialogFragment();
        myDialog.show(getFragmentManager(), "MyDialog");
    }

}
