data class SolarResponse(
    val estimates: List<SolarEstimate>
)

data class SolarEstimate(
    val ghi: Double,   // Global Horizontal Irradiance (solar power received)
    val dni: Double    // Direct Normal Irradiance
)
