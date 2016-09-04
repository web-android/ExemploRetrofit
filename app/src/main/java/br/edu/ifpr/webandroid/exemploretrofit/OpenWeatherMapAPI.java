package br.edu.ifpr.webandroid.exemploretrofit;

import br.edu.ifpr.webandroid.exemploretrofit.model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by everaldo on 04/09/16.
 */
public interface OpenWeatherMapAPI {

    @GET("data/2.5/weather")
    Call<Weather> getWeatherReport(@Query("q") String query, @Query("APPID") String appID);
}
