package com.example.apicalls

import NasaPowerApiService
import NasaPowerResponse
import WeatherApiService
import WeatherResponse
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var etCity: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvTemperature: TextView
    private lateinit var ivTemperatureImage: ImageView
    private lateinit var tvWindSpeed: TextView
    private lateinit var ivWindSpeedImage: ImageView
    private lateinit var tvCloudCover: TextView
    private lateinit var ivCloudCoverImage: ImageView
    private lateinit var tvSolarRadiation: TextView
    private lateinit var ivSolarRadiationImage: ImageView
    private lateinit var tvClearSkySolarRadiation: TextView
    private lateinit var ivClearSkySolarRadiationImage: ImageView
    private lateinit var tvLongwaveRadiation: TextView
    private lateinit var ivLongwaveRadiationImage: ImageView
    private lateinit var tvTopAtmosphereSolarRadiation: TextView
    private lateinit var ivTopAtmosphereSolarRadiationImage: ImageView
    private lateinit var tvSolarEnergyPotential: TextView
    private lateinit var ivSolarEnergyPotentialImage: ImageView
    private lateinit var tvWindPowerPotential: TextView
    private lateinit var ivWindPowerPotentialImage: ImageView

    private val WEATHER_API_KEY = "315f293daca93eaf2847d326cec66326"
    private val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val NASA_BASE_URL = "https://power.larc.nasa.gov/"

    private val DEFAULT_CITY = "Jalandhar"
    private val DEFAULT_LAT = 31.3260
    private val DEFAULT_LON = 75.5762

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weatherdata)

        etCity = findViewById(R.id.etCity)
        btnSearch = findViewById(R.id.btnSearch)
        tvTemperature = findViewById(R.id.tvTemperature)
        ivTemperatureImage = findViewById(R.id.ivTemperatureImage)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)
        ivWindSpeedImage = findViewById(R.id.ivWindSpeedImage)
        tvCloudCover = findViewById(R.id.tvCloudCover)
        ivCloudCoverImage = findViewById(R.id.ivCloudCoverImage)
        tvSolarRadiation = findViewById(R.id.tvSolarRadiation)
        ivSolarRadiationImage = findViewById(R.id.ivSolarRadiationImage)
        tvClearSkySolarRadiation = findViewById(R.id.tvClearSkySolarRadiation)
        ivClearSkySolarRadiationImage = findViewById(R.id.ivClearSkySolarRadiationImage)
        tvLongwaveRadiation = findViewById(R.id.tvLongwaveRadiation)
        ivLongwaveRadiationImage = findViewById(R.id.ivLongwaveRadiationImage)
        tvTopAtmosphereSolarRadiation = findViewById(R.id.tvTopAtmosphereSolarRadiation)
        ivTopAtmosphereSolarRadiationImage = findViewById(R.id.ivTopAtmosphereSolarRadiationImage)
        tvSolarEnergyPotential = findViewById(R.id.tvSolarEnergyPotential)
        ivSolarEnergyPotentialImage = findViewById(R.id.ivSolarEnergyPotentialImage)
        tvWindPowerPotential = findViewById(R.id.tvWindPowerPotential)
        ivWindPowerPotentialImage = findViewById(R.id.ivWindPowerPotentialImage)

//        // Set initial visibility to gone
//        tvTemperature.visibility = View.GONE
//        ivTemperatureImage.visibility = View.GONE
//        tvWindSpeed.visibility = View.GONE
//        ivWindSpeedImage.visibility = View.GONE
//        tvCloudCover.visibility = View.GONE
//        ivCloudCoverImage.visibility = View.GONE
//        tvSolarRadiation.visibility = View.GONE
//        ivSolarRadiationImage.visibility = View.GONE
//        tvClearSkySolarRadiation.visibility = View.GONE
//        ivClearSkySolarRadiationImage.visibility = View.GONE
//        tvLongwaveRadiation.visibility = View.GONE
//        ivLongwaveRadiationImage.visibility = View.GONE
//        tvTopAtmosphereSolarRadiation.visibility = View.GONE
//        ivTopAtmosphereSolarRadiationImage.visibility = View.GONE
//        tvSolarEnergyPotential.visibility = View.GONE
//        ivSolarEnergyPotentialImage.visibility = View.GONE
//        tvWindPowerPotential.visibility = View.GONE
//        ivWindPowerPotentialImage.visibility = View.GONE


        fetchWeatherData(DEFAULT_CITY, DEFAULT_LAT, DEFAULT_LON)
        val tvDefaultCity: TextView = findViewById(R.id.tvDefaultCity)


        btnSearch.setOnClickListener {
            val city = etCity.text.toString().trim()

            if (city.isNotEmpty()) {
                fetchWeatherData(city, DEFAULT_LAT, DEFAULT_LON)
            } else {
                tvTemperature.text = "Please enter a city name"
            }
        }
    }

    private fun createRetrofitClient(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun fetchWeatherData(city: String, DEFAULT_LAT: Double, DEFAULT_LON: Double) {
        val retrofit = createRetrofitClient(WEATHER_BASE_URL)
        val apiService = retrofit.create(WeatherApiService::class.java)
        apiService.getWeather(city, WEATHER_API_KEY, "metric")
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            val temperature = weatherData.main.temp
                            val windSpeed = weatherData.wind.speed
                            val cloudCover = weatherData.clouds.all
                            fetchSolarAndWindData(28.61, 77.23, windSpeed, cloudCover) // Example for Delhi
                            tvTemperature.text = "Temperature: $temperature°C"
                            tvWindSpeed.text = "Wind Speed: $windSpeed m/s"
                            tvCloudCover.text = "Current Cloud Cover: $cloudCover%"

                            // Set images based on temperature
                            if (temperature > 30) {
                                ivTemperatureImage.setImageResource(R.drawable.hot_temperature_image)
                            } else if (temperature < 10) {
                                ivTemperatureImage.setImageResource(R.drawable.cold_temperature_image)
                            } else {
                                ivTemperatureImage.setImageResource(R.drawable.default_temperature_image)
                            }

                            // Set visibility to visible
//                            tvTemperature.visibility = View.VISIBLE
//                            ivTemperatureImage.visibility = View.VISIBLE
//                            tvWindSpeed.visibility = View.VISIBLE
//                            ivWindSpeedImage.visibility = View.VISIBLE
//                            tvCloudCover.visibility = View.VISIBLE
//                            ivCloudCoverImage.visibility = View.VISIBLE
                        }
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        tvTemperature.text = "Error fetching weather: $errorBody"
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    tvTemperature.text = "Network error: ${t.message}"
                }
            })
    }

    private fun fetchSolarAndWindData(lat: Double, lon: Double, windSpeed: Float, cloudCover: Int) {
        val retrofit = createRetrofitClient(NASA_BASE_URL)
        val apiService = retrofit.create(NasaPowerApiService::class.java)
        val startDate = "20240220"
        val endDate = "20240227"
        apiService.getSolarData(lat, lon, startDate, endDate)
            .enqueue(object : Callback<NasaPowerResponse> {
                override fun onResponse(
                    call: Call<NasaPowerResponse>,
                    response: Response<NasaPowerResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val parameters = responseBody?.properties?.parameter
                        val ghi = parameters?.ALLSKY_SFC_SW_DWN?.values?.lastOrNull() ?: 0.0
                        val clearSkySolar = parameters?.CLRSKY_SFC_SW_DWN?.values?.lastOrNull() ?: 0.0
                        val longwaveRadiation = parameters?.ALLSKY_SFC_LW_DWN?.values?.lastOrNull() ?: 0.0
                        val topAtmosphereSolar = parameters?.ALLSKY_TOA_SW_DWN?.values?.lastOrNull() ?: 0.0
                        val solarPower = calculateSolarPower(ghi, cloudCover)
                        val windPower = calculateWindPower(windSpeed)
                        tvSolarRadiation.text = "Solar Radiation (Surface): $ghi W/m²"
                        tvClearSkySolarRadiation.text = "Clear Sky Solar Radiation: $clearSkySolar W/m²"
                        tvLongwaveRadiation.text = "Longwave Radiation (Sky): $longwaveRadiation W/m²"
                        tvTopAtmosphereSolarRadiation.text = "Total Solar at Top of Atmosphere: $topAtmosphereSolar W/m²"
                        val solarMessage = when {
                            solarPower > 100 -> "High Solar Energy Potential!"
                            solarPower in 50.0..100.0 -> "Moderate Solar Energy."
                            else -> "Low Solar Energy Potential."
                        }
                        val windMessage = when {
                            windPower > 200 -> "High Wind Power Potential!"
                            windPower in 100.0..200.0 -> "Moderate Wind Power."
                            else -> "Low Wind Energy Potential."
                        }
                        tvSolarEnergyPotential.text = solarMessage
                        tvWindPowerPotential.text = windMessage

                        // Set images based on solar and wind power
                        if (solarPower > 100) {
                            ivSolarRadiationImage.setImageResource(R.drawable.high_solar_power_image)
                        } else if (solarPower in 50.0..100.0) {
                            ivSolarRadiationImage.setImageResource(R.drawable.moderate_solar_power_image)
                        } else {
                            ivSolarRadiationImage.setImageResource(R.drawable.low_solar_power_image)
                        }

                        if (windPower > 200) {
                            ivWindPowerPotentialImage.setImageResource(R.drawable.high_wind_power_image)
                        } else if (windPower in 100.0..200.0) {
                            ivWindPowerPotentialImage.setImageResource(R.drawable.moderate_wind_power_image)
                        } else {
                            ivWindPowerPotentialImage.setImageResource(R.drawable.low_wind_power_image)
                        }

                        // Set visibility to visible
                        tvSolarRadiation.visibility = View.VISIBLE
                        ivSolarRadiationImage.visibility = View.VISIBLE
                        tvClearSkySolarRadiation.visibility = View.VISIBLE
                        ivClearSkySolarRadiationImage.visibility = View.VISIBLE
                        tvLongwaveRadiation.visibility = View.VISIBLE
                        ivLongwaveRadiationImage.visibility = View.VISIBLE
                        tvTopAtmosphereSolarRadiation.visibility = View.VISIBLE
                        ivTopAtmosphereSolarRadiationImage.visibility = View.VISIBLE
                        tvSolarEnergyPotential.visibility = View.VISIBLE
                        ivSolarEnergyPotentialImage.visibility = View.VISIBLE
                        tvWindPowerPotential.visibility = View.VISIBLE
                        ivWindPowerPotentialImage.visibility = View.VISIBLE
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        tvSolarRadiation.text = "Error fetching NASA solar data: $errorBody"
                    }
                }

                override fun onFailure(call: Call<NasaPowerResponse>, t: Throwable) {
                    tvSolarRadiation.text = "Network error: ${t.message}"
                }
            })
    }

    private fun calculateSolarPower(ghi: Double, cloudCover: Int, panelArea: Double = 1.5, efficiency: Double = 0.18): Double {
        return ghi * (1 - cloudCover / 100.0) * panelArea * efficiency
    }

    private fun calculateWindPower(windSpeed: Float, bladeRadius: Double = 1.5, airDensity: Double = 1.225, efficiency: Double = 0.4): Double {
        val rotorArea = Math.PI * bladeRadius * bladeRadius
        return 0.5 * airDensity * rotorArea * Math.pow(windSpeed.toDouble(), 3.0) * efficiency
    }
}