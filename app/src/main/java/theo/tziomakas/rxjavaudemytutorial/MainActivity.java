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
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
<<<<<<< Updated upstream
 * Async Subject emmits all the subsequent items
 * of the Observable at the time of subscription.
=======
 * ReplaySubject emits all the items of the source Observable,
 * regardless of when the subscriber subscribes.
>>>>>>> Stashed changes
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doSomeWork();
    }

    private void doSomeWork(){
        ReplaySubject<Integer> source = ReplaySubject.create().create();

        source.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getSecondObserver());

        source.onNext(4);

        source.onComplete();
    }

    private Observer<Integer> getFirstObserver(){
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "First Observer onSubscribe");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "First Observer onNext value: " + s);
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

    private Observer<Integer> getSecondObserver(){
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Second Observer onSubscribe");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "Second Observer onNext value: " + s);
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


}