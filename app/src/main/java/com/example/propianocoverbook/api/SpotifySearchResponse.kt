package com.example.propianocoverbook.api

data class SpotifySearchResponse(
    val tracks: SpotifyTracks,
    val artists: SpotifyArtists
)

data class SpotifyTracks(
    val items: List<Track>
)

data class SpotifyArtists(
    val items: List<Artist>
)

data class Track(
    val name: String,
    val artists: List<Artist>
)

data class Artist(
    val name: String
)
