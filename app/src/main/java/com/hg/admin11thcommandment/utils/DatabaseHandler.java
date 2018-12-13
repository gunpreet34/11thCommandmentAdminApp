package com.hg.admin11thcommandment.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DatabaseHandler {
    RequestQueue requestQueue;
    public Context mContext;
    String url = "https://commandment-api.herokuapp.com/";
    //String url = "http://192.168.1.102:3000/";
    private SharedPrefUtil mUtil;
    private int statusCode;

    //Constructor
    public DatabaseHandler(Context context){
        //For requests, create a queue
        mContext = context;
        requestQueue = Volley.newRequestQueue(context);
        mUtil = new SharedPrefUtil(mContext);
    }

    //Admin login
    public void adminLogin(final Map<String, String> data,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"adminLogin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    //For News

    //Post new news/poll
    public void postNews(final Map<String, String> data){
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"postNews", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams()
            {
                data.put("user_id",mUtil.getToken());
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Update News
    public void updateNews(final Map<String,String> data){
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"updateNews", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams()
            {
                data.put("user_id",mUtil.getToken());
                Log.d("DATA SENT FOR UPDATE",data.toString());
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Search news by title
    public void searchNewsByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("user_id",mUtil.getToken());
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"searchVerifiedNewsByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Search unverified news by title
    public void searchUnverifiedNewsByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("user_id",mUtil.getToken());
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"searchUnverifiedNewsByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Delete news
    public void deleteNews(final Map<String,String> data){
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"deleteNews", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(mContext, "Error in delete news response", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                data.put("user_id",mUtil.getToken());
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //GET NEWS

    //Get Verified news
    public void getVerifiedNews(final VolleyCallback callback) {
        //Request a GET method to save news with ResponseListener and ResponseErrorListener
        String getNewsUrl = url+"getNews/";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, getNewsUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(getRequest);
    }

    //Get Unverified news
    public void getUnverifiedNews(final VolleyCallback callback) {
        //Request a GET method to save news with ResponseListener and ResponseErrorListener
        String getNewsUrl = url+"getUnverifiedNews";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, getNewsUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(getRequest);
    }


    //CATEGORIES

    //Add new category
    public void addCategory(final Map<String, String> data){
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"addCategory", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Get all categories
    public void getCategories(final VolleyCallback callback) {
        //Request a GET method to save news with ResponseListener and ResponseErrorListener
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url+"getCategories", null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(getRequest);
    }

    public void getAllAdvertisements(final VolleyCallback callback) {
        //Request a GET method to save news with ResponseListener and ResponseErrorListener
        String getNewsUrl = url+"getAllAdvertisements/"+mUtil.getToken();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, getNewsUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(getRequest);
    }

    public void getUnverifiedAdvertisements(final VolleyCallback callback) {
        //Request a GET method to save news with ResponseListener and ResponseErrorListener
        String getNewsUrl = url+"getUnverifiedAdvertisements";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, getNewsUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(getRequest);
    }






    //Search advertisement by title
    public void searchAdvertisementByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("user_id",mUtil.getToken());
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"searchAdvertisementByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //Search unverified advertisement by title
    public void searchUnverifiedAdvertisementByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("user_id",mUtil.getToken());
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"searchUnverifiedAdvertisementByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //GetNewsByTitle
    public void getNewsByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"getNewsByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //GetNewsByTitle
    public void getAdvertisementByTitle(final VolleyCallback callback,String title) {
        //Request a POST method to save news with ResponseListener and ResponseErrorListener
        final Map<String,String> data = new HashMap<>();
        data.put("title",title);
        //Log.d("TITLE",data.get("title"));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"getAdvertisementByTitle", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() {
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }



}
