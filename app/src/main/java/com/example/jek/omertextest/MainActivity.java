package com.example.jek.omertextest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jek.omertextest.adapters.PostAdapter;
import com.example.jek.omertextest.intefaces.FlickrInterface;
import com.example.jek.omertextest.intefaces.PostInterface;
import com.example.jek.omertextest.model.FlickrPhotos;
import com.example.jek.omertextest.model.Post;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jek.omertextest.Constants.BASE_FLICKR_URL;
import static com.example.jek.omertextest.Constants.BASE_JSONPLACEHOLDER_URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompositeDisposable mCompositeDisposable;
    private ArrayList<Post> mPostList;
    private PostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.rvPost);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCompositeDisposable = new CompositeDisposable();
        loadJSON();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    private void loadJSON() {
        //Post
        PostInterface postInterface = new Retrofit.Builder()
                .baseUrl(BASE_JSONPLACEHOLDER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PostInterface.class);

        //Flickr
        FlickrInterface flickrInterface = new Retrofit.Builder()
                .baseUrl(BASE_FLICKR_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(FlickrInterface.class);

        Observable<FlickrPhotos> photos = flickrInterface.getPhotos();
        Observable<Post[]> posts = postInterface.getPosts();

        Disposable subscription = photos
                .map((p) -> p.getPhotos().getPhoto())
                .zipWith(posts, this::zip)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
        mCompositeDisposable.add(subscription);
    }

    private void handleResponse(ArrayList<Post> posts) {
        mPostList = new ArrayList<>(posts);
        mAdapter = new PostAdapter(mPostList);
        recyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Post> zip(FlickrPhotos.FlickrPhotoSettings[] photoSettings, Post[] posts) {
        ArrayList<Post> list = new ArrayList<>();
        for (int i = 0; i < photoSettings.length; i++) {
            posts[i].setUrl(photoSettings[i].getUrl());
            list.add(posts[i]);
        }
        return list;
    }
}
