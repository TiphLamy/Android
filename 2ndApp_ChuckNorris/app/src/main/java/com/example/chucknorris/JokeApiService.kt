package com.example.chucknorris

import io.reactivex.Single
import retrofit2.http.GET

interface JokeApiService {
    @GET("Joke")
    fun giveMeAJoke(): Single<Joke>
}