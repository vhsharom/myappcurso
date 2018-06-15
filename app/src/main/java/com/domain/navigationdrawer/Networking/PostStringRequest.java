package com.domain.navigationdrawer.Networking;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostStringRequest extends StringRequest {

    private HashMap<String, String> post;

    public PostStringRequest(HashMap<String, String> post, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.post = post;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return post;
    }


}
