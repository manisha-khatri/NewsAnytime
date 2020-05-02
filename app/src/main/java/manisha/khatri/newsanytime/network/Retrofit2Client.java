package manisha.khatri.newsanytime.network;

import manisha.khatri.newsanytime.util._enum.APIServiceConst;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit retrofit;
    private Retrofit2Client(){}

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(APIServiceConst.BASE_URL.toString())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
