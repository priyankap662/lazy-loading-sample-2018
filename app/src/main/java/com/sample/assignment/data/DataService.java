package com.sample.assignment.data;


import com.sample.assignment.models.Destination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface DataService {

    @Headers({"cache: true",})
    @GET("/s/2iodh4vg0eortkl/facts")
    Call<Destination> fetchDestination(@Header("refresh") boolean... refresh);
}
