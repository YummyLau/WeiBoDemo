package com.example.yummylau.rapiddvpt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        Observable.just("a","b")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.print(s);
                    }
                });
    }
}