package com.example.ayesh.youtubedata;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button b;
    RequestQueue rq;

    ArrayAdapter<String> adapter;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv= findViewById(R.id.lv);
        b = findViewById(R.id.but1);
        rq = Volley.newRequestQueue(this);

        al = new ArrayList<>();
    }


    public void dothis(View V)
    {
        String url = "Link"

        final JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jarr = response.getJSONArray("items");
                            for(int i=0; i<jarr.length();i++)
                            {
                                JSONObject thumbnail = jarr.getJSONObject(i);
                                JSONObject snippets =thumbnail.getJSONObject("snippet");
                                JSONObject defaulturl= snippets.getJSONObject("thumbnails");
                                JSONObject url = defaulturl.getJSONObject("high");
                                JSONObject resourceId = thumbnail.getJSONObject("id");

                                String videoId=resourceId.getString("videoId");
                                String imageurl = url.getString("url");
                                String title = snippets.getString("title");
                                String description = snippets.getString("description");
                               String s = "Video ID = "+videoId + "\n url =  "+imageurl+"\n title= "+title+"\n description= "+description;
                                Log.i("Value", "The values are "+s);
                                al.add(s);
                                adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,al);
                                lv.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             error.printStackTrace();
            }
        }

        );

        rq.add(jreq);
    }
}
