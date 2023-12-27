import retrofit2.Call
import retrofit2.http.GET


interface WeatherService {
    @GET("forecast?latitude=41.0082&longitude=28.9784&current=temperature_2m,weather_code")
    fun getWeather(): Call<Weather>
}