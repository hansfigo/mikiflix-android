package com.example.mikiflix


data class AnimeResponse (
    val results : List<AnimeData>
)

data class AnimeData(
    val id: String,
    val malId: Int,
    val title: Title,
    val status: String,
    val image: String,
    val cover: String,
    val popularity: Int,
    val description: String,
    val rating: Int,
    val genres: List<String>,
    val color: String,
    val totalEpisodes: Int,
    val currentEpisodeCount: Int,
    val type: String,
    val releaseDate: Int
)

data class Title(
    val romaji: String,
    val english: String,
    val native: String,
    val userPreferred: String
)

data class AnimeInfo(
    val id: String?,
    val title: Title?,
    val malId: Int?,
    val synonyms: List<String>?,
    val isLicensed: Boolean?,
    val isAdult: Boolean?,
    val countryOfOrigin: String?,
    val trailer: Trailer?,
    val image: String?,
    val popularity: Int?,
    val color: String?,
    val cover: String?,
    val description: String?,
    val status: String?,
    val releaseDate: Int?,
    val startDate: Date?,
    val endDate: Date?,
    val nextAiringEpisode: NextAiringEpisode?,
    val totalEpisodes: Int?,
    val currentEpisode: Int?,
    val rating: Double?,
    val duration: Int?,
    val genres: List<String>?,
    val season: String?,
    val studios: List<String>?,
    val episodes: List<Episode>?,
    val relations: List<Relation>?,
    val characters: List<Character>?,
    val recommendations: List<Recommendation>?
)

data class Trailer(
    val id: String?,
    val site: String?,
    val thumbnail: String?
)

data class Date(
    val year: Int?,
    val month: Int?,
    val day: Int?
)

data class NextAiringEpisode(
    val airingTime: Int?,
    val timeUntilAiring: Int?,
    val episode: Int?
)

data class Episode(
    val id: String,
    val title: String?,
    val description: String?,
    val number: Int,
    val image: String,
    val airDate: String?
)

data class Recommendation(
    val id: String,
    val malId: String,
    val title: Title,
    val status: String,
    val episodes: Int,
    val image: String,
    val cover: String,
    val rating: Double,
    val type: String
)

data class Character(
    val id: Int,
    val role: String,
    val name: Name,
    val image: String,
    val voiceActors: List<VoiceActor>
)

data class Name(
    val first: String,
    val last: String,
    val full: String,
    val native: String?
)

data class VoiceActor(
    val id: Int,
    val language: String,
    val name: Name,
    val image: String
)

data class Relation(
    val id: Int,
    val relationType: String,
    val malId: Int,
    val title: Title,
    val status: String,
    val episodes: Int?,
    val image: String,
    val color: String,
    val type: String,
    val cover: String,
    val rating: Double
)


