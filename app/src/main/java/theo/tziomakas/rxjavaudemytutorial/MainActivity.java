package theo.tziomakas.rxjavaudemytutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private String greeting = "Hello from RxJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    //private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable = Observable.just(greeting);

//        myObservable.subscribeOn(Schedulers.io());
//
//        myObservable.observeOn(AndroidSchedulers.mainThread());

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

//        compositeDisposable.add(myObserver);
//        myObservable.subscribe(myObserver);
        compositeDisposable.add(
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver)
        );

        myObserver2 = new DisposableObserver<String>(){

            @Override
            public void onNext(String s) {
                Log.d(TAG, "observer's 2 onNext invoked");

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "observer's 2 onError invoked");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "observer's 2 onComplete invoked");

            }
        };

//        compositeDisposable.add(myObserver2);
//        myObservable.subscribe(myObserver2);
        compositeDisposable.add(
                myObservable.subscribeWith(myObserver2)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //disposable.dispose();
        compositeDisposable.clear();
    }
}
