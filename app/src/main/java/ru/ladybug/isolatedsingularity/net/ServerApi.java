package ru.ladybug.isolatedsingularity.net;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.ActionReportResponse;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.AuthReportResponse;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.JChain;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.JContrib;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.JUser;
import ru.ladybug.isolatedsingularity.net.retrofitmodels.MakeContribBody;

public interface ServerApi {
    @GET("chains/{id}")
    Call<JChain> getChainById(@Path("id") int id, @Query("token") String token);
    @GET("chains/")
    Call<List<JChain>> getChains(@Query("token") String token);
    @GET("chains/contrib/{id}")
    Call<List<JContrib>> getContribsByChain(@Path("id") int id, @Query("token") String token);

    @POST("chains/action/")
    Call<ActionReportResponse> makeContrib(@Body MakeContribBody body);

    @GET("user")
    Call<JUser> getUserData(@Query("token") String token);

    @GET("login")
    Call<AuthReportResponse> tryLogin(@Query("username") String username, @Query("password") String password);


    @GET("register")
    Call<AuthReportResponse> tryRegister(@Query("username") String username, @Query("password") String password);
}
