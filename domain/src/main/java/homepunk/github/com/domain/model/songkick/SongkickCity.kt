package homepunk.github.com.domain.model.songkick

data class SongkickCity(
        val country: SongkickCountry? = null,
        var lat: Double,
        var lng: Double,
        var displayName: String = "",
        val id: Int? = null,
        val uri: String? = null
)
