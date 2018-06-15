package com.domain.navigationdrawer;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {


    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = getView().findViewById(R.id.broadcastButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.code3e.MY_CUSTOM_ACTION");
                intent.putExtra("value", 100);
                getActivity().sendBroadcast(intent);


                Intent local = new Intent("com.code3e.MY_LOCAL_CUSTOM_ACTION");
                local.putExtra("value", 100);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(local);

                Intent intentScan = new Intent(getActivity(), ScannerActivity.class);
                startActivity(intentScan);

            }
        });

    }

}












