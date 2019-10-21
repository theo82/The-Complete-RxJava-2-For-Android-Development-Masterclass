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
    //private String greetings = "Hello A,Hello B,Hello C";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable = Observable.just("Hello A","Hello B","Hello C");

        compositeDisposable.add(
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        );

    }

    DisposableObserver getObserver(){

        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext invoked" + s);
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

        return myObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //disposable.dispose();
        compositeDisposable.clear();
    }
}
