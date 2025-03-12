import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SolarApiService {
    @GET("radiation/forecasts")
    fun getSolarData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("api_key") apiKey: String
    ): Call<SolarResponse>
}
