package com.example.jek.omertextest.intefaces;

import com.example.jek.omertextest.model.Post;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("/posts")
    Observable<Post[]> getPosts();
}
