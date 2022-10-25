package com.example.tdd.other.api;

import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

public class APIServiceTest {

    private ApiService apiService;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Before
    public void setup() {

        OkHttpClient client = new OkHttpClient();

        apiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(ApiService.class);
    }

    @Test
    public void product() {
        TODO todo = apiService.getProduct().blockingGet();
        assertEquals(String.valueOf(1), todo.getUserId().toString());
        assertEquals(String.valueOf(1), todo.getId().toString());
        assertEquals("delectus aut autem", todo.getTitle());
        assertEquals(false, todo.getCompleted());
    }

}
