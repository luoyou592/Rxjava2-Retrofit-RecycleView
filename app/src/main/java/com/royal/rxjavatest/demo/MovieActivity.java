package com.royal.rxjavatest.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.royal.rxjavatest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 25505 on 2017/7/23.
 */

public class MovieActivity extends AppCompatActivity {


    @BindView(R.id.rv_movie)
    RecyclerView mRvMovie;
    private RecyclerView.Adapter mAdapter;
    private List<ResponseInfo.SubjectsBean> mMovieList = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mContext = this;
        ButterKnife.bind(this);
        //请求数据并设置适配器
        loadServerData();
    }


    private void loadServerData() {
        //0代表从第0条开始，10表示一次请求取10条，具体看获得请求数据
        //通过单例模式获得请求接口
        Observable<ResponseInfo> movieCall = MovieRetrofit.getInstance().getMovieService().getMovieList(0, 20);
        movieCall.subscribeOn(Schedulers.io())
                .map(new Function<ResponseInfo, List<ResponseInfo.SubjectsBean>>() {
                    @Override
                    public List<ResponseInfo.SubjectsBean> apply(ResponseInfo responseInfo) throws Exception {
                        for(int i=0;i<20;i++){
                            mMovieList.add(responseInfo.getSubjects().get(i));
                        }
                        return mMovieList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //此方法是让下面onNext在主线程消费事件
                .subscribe(new Observer<List<ResponseInfo.SubjectsBean>>() {
                    //当Observable调用subscribe方法时会回调该方法（运行在主线程）
                    @Override
                    public void onSubscribe(Disposable d) {
                        //设置recycleview的方向（水平方向）
                        mRvMovie.setLayoutManager(new LinearLayoutManager(mContext,
                                LinearLayoutManager.HORIZONTAL,false));
                    }
                    //onSubscribe方法后调用，此方法是接收上面map()返回的数据对象
                    @Override
                    public void onNext(List<ResponseInfo.SubjectsBean> value) {
                        mAdapter = new MovieRvAdapter(mContext,value);
                        mRvMovie.setAdapter(mAdapter); //设置recycleview适配器

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
