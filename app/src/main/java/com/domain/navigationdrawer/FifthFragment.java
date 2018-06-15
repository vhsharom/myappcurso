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
import android.widget.Toast;

import com.domain.navigationdrawer.Adapters.MyRecyclerViewAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class FifthFragment extends Fragment {

    private Button chatButton;
    private EditText chatEditText;
    private RecyclerView chatRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<String> chatMessages;

    private Socket socket;
    private String IP = "192.168.1.75";
    private int PORT = 1080;

    public FifthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifth, container, false);
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
        new Thread(new ChatThread()).start();

        //
    }

    private void sendMessage(){
        final String chatMessage = chatEditText.getText().toString();
        if (chatMessage.isEmpty() == false){
            if (socket != null && socket.isConnected()){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String message = "chatMessage: " + chatMessage;
                            byte[] bytes = message.getBytes();
                            socket.getOutputStream().write(bytes);
                            Log.d("App", "Message sended");
                        }catch (Exception e){
                            Log.d("App", e.getMessage());
                        }
                    }
                });
                thread.start();
            }
            chatEditText.setText("");
        }
    }

    private void registerUser(){
        if (socket != null && socket.isConnected()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String name = "vhsharo";
                        String message = "user: " + name;
                        byte[] bytes = message.getBytes();
                        socket.getOutputStream().write(bytes);
                        Log.d("App", "Message sended");
                    }catch (Exception e){
                        Log.d("App", e.getMessage());
                    }
                }
            });
            thread.start();
        }
    }

    class ChatThread implements Runnable{

        @Override
        public void run() {

            try {

                socket = new Socket(IP, PORT);
                Log.d("App", "Connected to Server");

                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        registerUser();
                    }
                }, 1000);

                String message;
                while ((message = bufferedReader.readLine()) != null){
                    Log.d("App", "Data received: " + message);

                    chatMessages.add(message);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
                        }
                    });

                }
            }catch (Exception e){
                Log.d("App", e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

    }

}





























