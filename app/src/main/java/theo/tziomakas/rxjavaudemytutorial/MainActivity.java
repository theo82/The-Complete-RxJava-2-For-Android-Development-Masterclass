package theo.tziomakas.rxjavaudemytutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private String greeting = "Hello from RxJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    //private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable = Observable.just(greeting);

        myObservable.subscribeOn(Schedulers.io());

        myObservable.observeOn(AndroidSchedulers.mainThread());


        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext invoked");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError invoked");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete invoked");
            }
        };

        myObservable.subscribe(myObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //disposable.dispose();
        myObserver.dispose();
    }
}
