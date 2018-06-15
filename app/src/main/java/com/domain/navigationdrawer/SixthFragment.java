package com.domain.navigationdrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.domain.navigationdrawer.Networking.PostStringRequest;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONObject;

import java.util.HashMap;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;


/**
 * A simple {@link Fragment} subclass.
 */
public class SixthFragment extends Fragment {


    private CardInputWidget cardInputWidget;


    public SixthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sixth, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cardInputWidget = getView().findViewById(R.id.cardInputWidget);

        Button scanButton = getView().findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CardIOActivity.class);
                intent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
                startActivityForResult(intent, 2018);

            }
        });

        Button payButton = getView().findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });

    }

    private void processPayment(){
        Card card = cardInputWidget.getCard();
        if (card.validateCard()){
            Stripe stripe
                    = new Stripe(getActivity(), "pk_test_ihKovFGxcrGGDoV57lkWRtax");
            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Token token) {
                    Log.d("App", "Token success");
                    sendPayment(token);
                }
            });

        }else{
            Toast.makeText(getActivity(), "Tarjeta invalida", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPayment(Token token){

        String url = "http://192.168.1.75/donate/payment.php";
        int method = Request.Method.POST;

        HashMap<String, String> post = new HashMap<>();
        post.put("stripeToken", token.getId());
        post.put("amount", "1000");
        post.put("currency", "MXN");
        post.put("description", "Donacion voluntaria");

        PostStringRequest postStringRequest = new PostStringRequest(post, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("App", "response: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("Success")){
                        Toast.makeText(getActivity(), "Pago exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Error al pagar", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postStringRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
            CreditCard creditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            Log.d("App","Card number: " + creditCard.cardNumber);
            Log.d("App","Month: " + creditCard.expiryMonth);
            Log.d("App","Day: " + creditCard.expiryYear);
            cardInputWidget.setCardNumber(creditCard.cardNumber);
            cardInputWidget.setExpiryDate(creditCard.expiryMonth, creditCard.expiryYear);

        }
    }

}






































