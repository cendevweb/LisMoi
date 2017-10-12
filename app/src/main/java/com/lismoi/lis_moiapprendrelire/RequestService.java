package com.lismoi.lis_moiapprendrelire;

import com.lismoi.lis_moiapprendrelire.model.WordsList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Phil on 12/10/2017.
 */

public interface RequestService {
    String ENDPOINT = "http://philemoncombes.com";

    @GET("/lismoi/lismoiquery")
    void getAllWords(Callback<WordsList> callback);
}
