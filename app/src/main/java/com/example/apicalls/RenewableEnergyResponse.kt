package com.example.apicalls

data class RenewableEnergyResponse(
    val energy_mix: EnergyMix
)

data class EnergyMix(
    val wind: Double?,
    val solar: Double?,
    val hydro: Double?,
    val carbon_intensity: Double?
)
