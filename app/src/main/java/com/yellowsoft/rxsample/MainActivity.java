package com.yellowsoft.rxsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    TextView txtPart1;
    Context context;

    Observable.OnSubscribe observableAction = new Observable.OnSubscribe<String>() {
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello, World!");
            subscriber.onCompleted();
        }
    };
    Observable<String> observable = Observable.create(observableAction);

    Subscriber<String> textViewSubscriber = new Subscriber<String>() {
        public void onCompleted() {}
        public void onError(Throwable e) {}
        public void onNext(String s) {
            txtPart1.setText(s);
        }
    };

    Subscriber<String> toastSubscriber = new Subscriber<String>() {
        public void onCompleted() {}
        public void onError(Throwable e) {}
        public void onNext(String s) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPart1 = (TextView) findViewById(R.id.part1text);
        context = this;

        // Let's create an Observable from the Action we declared above
        Observable<String> observable = Observable.create(observableAction);
        /* Since we are going to interact with the objects that are
         * on the UI, we need to observe the outcome of our computation
         * on the main thread
         */
        observable.observeOn(AndroidSchedulers.mainThread());
        // At this point, we can invoke the Subscribers
        observable.subscribe(textViewSubscriber);
        observable.subscribe(toastSubscriber);
    }
}
