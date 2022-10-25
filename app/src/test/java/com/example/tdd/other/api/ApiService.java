package com.example.tdd.other.api;

import io.reactivex.Single;
import retrofit2.http.GET;

interface ApiService {

    @GET("/todos/1")
    Single<TODO> getProduct();
}