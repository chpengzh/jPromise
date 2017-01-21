package com.chpengzh.jpromise;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chpengzh.kpromise.KPromise;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String clientId = UUID.randomUUID().toString();
        Promise.start("Some Group Name", new Starter<Integer>() {
            @Override
            public void onStart(@NonNull final Return<Integer> start) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start.next(1001);
                    }
                }, 1_000);

            }
        }).then("some step description here", new Task<Integer, String>() {
            @Override
            public void onTask(Integer data, @NonNull final Return<String> ret) {
                Log.d("MainActivity", "client id is " + clientId);
                Log.d("MainActivity", "data from last step is " + data);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ret.next(null);
                    }
                }, 1_000);

            }
        }).then(new Task<String, Void>() {
            @Override
            public void onTask(@Nullable String nullable, @NonNull Return<Void> ret) {
                Log.d("DemoActivity", "data from last step is " + nullable);

            }
        }).execute();

    }

}
