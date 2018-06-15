package com.domain.navigationdrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.domain.navigationdrawer.Adapters.MyRecyclerViewAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends Fragment {

    private Button chatButton;
    private EditText chatEditText;
    private RecyclerView chatRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<String> chatMessages;


    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chatButton = getView().findViewById(R.id.chatButton);
        chatEditText = getView().findViewById(R.id.chatEditText);
        chatRecyclerView = getView().findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatMessages = new ArrayList<>();
        chatMessages.add("Welcome to chat");

        adapter = new MyRecyclerViewAdapter(getActivity(), chatMessages);

        chatRecyclerView.setAdapter(adapter);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        readMessages();
    }

    private void sendMessage(){

        String message = chatEditText.getText().toString();
        if (message.isEmpty() == false){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("chatmessages");
            myRef.push().setValue(message);
            chatEditText.setText("");
        }
    }

    private void readMessages(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatmessages");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("App", "OnChildAdded: " + dataSnapshot.toString());
                String message = (String)dataSnapshot.getValue();
                chatMessages.add(message);
                adapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("App", "onChildChanged: " + dataSnapshot.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("App", "onChildRemoved: " + dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("App", "onChildMoved: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("App", "onCancelled");
            }
        });

    }


}





















