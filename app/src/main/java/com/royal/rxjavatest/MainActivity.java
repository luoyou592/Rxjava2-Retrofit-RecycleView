package com.royal.rxjavatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.royal.rxjavatest.demo.MovieActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_movie)
    Button mBtnMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //简单版本
        //helloWorldSimple();  //rxjava入门
        //filter();  //filter操作符
        // map();
        //map1();
        flatmap();
    }

    private void flatmap() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

    }

    private void map1() {
        Observer<String> observer = new Observer<String>() {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("luoyou", "s" + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        Student student1 = new Student("张三", 18);
        Student student2 = new Student("李四", 12);
        Student student3 = new Student("王五", 15);
        Student[] students = {student1, student2, student3};
        Observable<String> observable = Observable.fromArray(students)
                .map(new Function<Student, String>() {

                    @Override
                    public String apply(Student student) throws Exception {
                        return student.getName();
                    }
                });
        observable.subscribe(observer);
    }

    private void map() {
        Observer<Teacher> observer = new Observer<Teacher>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Teacher value) {
                Log.d("luoyou", "value" + value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Student student = new Student();
        student.setName("小三");
        student.setAge(18);
        student.setSex("男");
        Observable.just(student).map(new Function<Student, Teacher>() {
            @Override
            public Teacher apply(Student student) throws Exception {
                Log.d("luoyou", "student" + student.toString());
                Teacher teacher = new Teacher();
                teacher.setName(student.getName());
                teacher.setDuty("teach");
                teacher.setAge(student.getAge());
                return teacher;
            }
        }).subscribe(observer);


    }

    private void filter() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("luoyou", "s" + s);
            }
        };
        Observable.just("aa", "bb", "cc", "dd").filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                Log.e("luoyou", "s:" + s);
                if (s.equals("bb")) {
                    return true;
                }
                return false;

            }
        }).subscribe(consumer);
    }

    private void helloWorldSimple() {

        //创建消费者，消费者接受一个String类型的事件
        /*Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("luoyou", s);
            }
        };
        //被观察者发出Hello World, 并且指定该事件的消费者为consumer
        Observable.just("Hello World").subscribe(consumer);*/
        Observer<String> observer = new Observer<String>() {
            //当Observable调用subscribe方法时会回调该方法
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscibe: " + d);
            }

            //onSubscribe方法后调用
            @Override
            public void onNext(String value) {
                Log.e(TAG, "onNext: " + value);
            }

            //这里没有出错，没有被调用
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e);
            }

            //onNext之后调用
            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello world");
                e.onComplete();
            }
        });
        observable.subscribe(observer);
    }

    public void token() {

    }

    @OnClick(R.id.btn_movie)
    public void onClick() {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);

    }
}
