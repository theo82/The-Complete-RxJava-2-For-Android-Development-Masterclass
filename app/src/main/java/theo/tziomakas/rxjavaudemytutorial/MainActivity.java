package theo.tziomakas.rxjavaudemytutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Flatmap operator transforms the emmited items from an Observable into Observables, then
 * flattens the emmissions from those into a single Observale.
 */

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private Observable<Student> myObservable;
    private DisposableObserver<Student> myObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> studentArrayList = getStudents();

                for(Student student:studentArrayList){
                    emitter.onNext(student);
                }

                emitter.onComplete();
            }
        });

        compositeDisposable.add(
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Function<Student, ObservableSource<Student>>() {
                    @Override
                    public ObservableSource<Student> apply(Student student) throws Exception {

                        Student student1 = new Student();
                        student1.setName(student.getName());

                        Student student2 = new Student();
                        student2.setName(student.getName());

                        student.setName(student.getName().toUpperCase());
                        return Observable.just(student, student1, student2);
                    }
                })
                .subscribeWith(getObserver())
        );

    }

    DisposableObserver getObserver(){

        myObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(Student s) {
                Log.d(TAG, s.getName());
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

    private ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setName(" student 1 ");
        student1.setEmail( " student1@gmail.com ");
        student1.setAge(27);
        students.add(student1);

        Student student2 = new Student();
        student2.setName(" student 2 ");
        student2.setEmail( " student2@gmail.com ");
        student2.setAge(27);
        students.add(student2);

        Student student3 = new Student();
        student3.setName(" student 3 ");
        student3.setEmail( " student3@gmail.com ");
        student3.setAge(27);
        students.add(student3);

        Student student4 = new Student();
        student4.setName(" student 4 ");
        student4.setEmail( " student4@gmail.com ");
        student4.setAge(27);
        students.add(student4);

        Student student5 = new Student();
        student5.setName(" student 5 ");
        student5.setEmail( " student5@gmail.com ");
        student5.setAge(27);
        students.add(student5);

        return students;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //disposable.dispose();
        compositeDisposable.clear();
    }
}
