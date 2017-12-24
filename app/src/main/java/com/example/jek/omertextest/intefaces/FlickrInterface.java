package com.example.jek.omertextest.intefaces;

import com.example.jek.omertextest.model.FlickrPhotos;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FlickrInterface {
    @GET("/services/rest/?method=flickr.photos.getRecent&format=json&api_key=322bd437114cc491daa1c53ad78b512b&nojsoncallback=?")
    Observable<FlickrPhotos> getPhotos();
}
