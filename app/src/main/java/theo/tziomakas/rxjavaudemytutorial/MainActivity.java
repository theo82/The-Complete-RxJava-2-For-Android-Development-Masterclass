package theo.tziomakas.rxjavaudemytutorial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;

/**
 * Async Subject only emmits the last value of the source Observable
 * only after that source Observable completes.
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncSubjectDemo1();
    }

    public void asyncSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA","KOTLIN","XML","JSON");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        observable.subscribe(asyncSubject);

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.subscribe(getThirdObserver());
    }

    private Observer<String> getFirstObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "First Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "First Observer onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "First Observer onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "First Observer onComplete");
            }
        };

        return observer;
    }

    private Observer<String> getSecondObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Second Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Second Observer onNext" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Second Observer onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Second Observer onComplete");
            }
        };

        return observer;
    }

    private Observer<String> getThirdObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Third Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Third Observer onNext" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Third Observer onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Third Observer onComplete");
            }
        };

        return observer;
    }
}