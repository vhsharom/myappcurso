package com.domain.navigationdrawer;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.domain.navigationdrawer.Adapters.MyRecyclerViewAdapter;
import com.domain.navigationdrawer.BroadcastReceivers.MyCustomBroadcastReceiver;
import com.domain.navigationdrawer.Security.RSA;
import com.domain.navigationdrawer.Services.MyIntentService;
import com.domain.navigationdrawer.Services.MyService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity{

    private MyCustomBroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private BroadcastReceiver otherReceiver;
    private AdView mAdView;

    private BroadcastReceiver localReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("App", "Local Message Received");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UA-120795268-1

        MobileAds.initialize(this, "ca-app-pub-8313710260493873~3727869557");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        receiver = new MyCustomBroadcastReceiver();
        intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        //android.net.conn.CONNECTIVITY_CHANGE

        registerReceiver(receiver, intentFilter);

        otherReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int value = intent.getIntExtra("value", 0);
                Log.d("App","Message Received Activity: " + value);

                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN);

                switch (wifistate){
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d("App", "WIFI_STATE_ENABLED");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d("App", "WIFI_STATE_DISABLED");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Log.d("App", "WIFI_STATE_DISABLING");
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.d("App", "WIFI_STATE_ENABLING");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Log.d("App", "WIFI_STATE_UNKNOWN");
                        break;
                    default:
                        break;
                }

            }
        };

        registerReceiver(otherReceiver, intentFilter);


        LocalBroadcastManager.getInstance(this).registerReceiver(
                localReceiver,
                new IntentFilter("com.code3e.MY_LOCAL_CUSTOM_ACTION"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ArrayList<String> dataSource = new ArrayList<>();
        for (int i = 0; i<10; i++){
            dataSource.add("Val: " + i);
        }

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, dataSource);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        adapter.setOnClickListener(new MyRecyclerViewAdapter.MyRecyclerListener() {
            @Override
            public void OnClick(View view, int position) {
                Log.d("App", "Position: " + position);

                switch (position){
                    case 0: {
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new FirstFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                    case 1:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new SecondFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                    case 2:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new ThirdFragment());
                        fragmentTransaction.commit();
                    }
                        break;
                    case 3:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new FourthFragment());
                        fragmentTransaction.commit();
                    }
                    case 4:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new FifthFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                    case 5:{
                        drawer.closeDrawer(GravityCompat.START);
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relativeLayout, new SixthFragment());
                        fragmentTransaction.commit();
                    }
                    break;
                    default:
                        break;
                }
            }
        });


        Log.d("App", "Start service");

        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo
                : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceInfo.service.getClassName().equals(MyService.class.getName())){
                Log.d("App", "Service is Active");
            }
        }

        requestLocation();

        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("value", 100);
        startService(intent);

        String password = "abc123";

        String md5 = getHash(password, "MD5");
        String sha1 = getHash(password, "SHA-1");
        String sha256 = getHash(password, "SHA-256");
        String sha512 = getHash(password, "SHA-512");

        Log.d("App", "Plain: " + password);
        Log.d("App", "md5: " + md5);
        Log.d("App", "sha1: " + sha1);
        Log.d("App", "sha256: " + sha256);
        Log.d("App", "sha512: " + sha512);

        try {
            String encrypted = encrytText(password);
            String decrypted = decryptText(encrypted);

            Log.d("App", "Encrypted: " + encrypted);
            Log.d("App", "Decrypted: " + decrypted);

            RSA rsa = new RSA();

            String rsaEncrypted = rsa.Encrypt(password);
            String rsaDecrypted = rsa.Decrypt(rsaEncrypted);

            Log.d("App", "rsaEncrypted: " + rsaEncrypted);
            Log.d("App", "rsaDecrypted: " + rsaDecrypted);


        }catch (Exception e){

        }

        FirebaseAnalytics mFirebaseAnalytics
                = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.PRICE, "200");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Drone");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);

    }

    String KEY = "12345678901234567890123456789012";
    String IV_VECTOR = "1234567890123456";

    public String encrytText(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] static_key = KEY.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(static_key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV_VECTOR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes());
        String result = Base64.encodeToString(results, Base64.NO_WRAP|Base64.DEFAULT);
        return result;
    }

    public String decryptText(String text)throws Exception{
        byte[] encryted_bytes = Base64.decode(text, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] static_key = KEY.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(static_key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV_VECTOR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encryted_bytes);
        String result = new String(decrypted);
        return result;
    }


    public String getHash(String plainText, String alg){
        try {
            MessageDigest digest = MessageDigest.getInstance(alg);
            digest.update(plainText.getBytes());
            byte[] encryptedBytes = digest.digest();
            StringBuilder builder = new StringBuilder();
            for (byte mByte : encryptedBytes){
                String hexString = Integer.toHexString(0XFF & mByte);
                while (hexString.length() < 2){
                    hexString = "0" + hexString;
                }
                builder.append(hexString);
            }
            return builder.toString();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(otherReceiver);
    }

    private void requestLocation(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            Intent intent = new Intent(this, MyService.class);
            startService(intent);

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 2018);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        requestLocation();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            stopService(new Intent(this, MyService.class));
        }
    }


}
