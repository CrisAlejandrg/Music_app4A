package com.example.music_app4a.model

//archivo para verificar si obtenemos una respuesta del api

data class ITunesResponse(

    val resultCount: Int,
    val results: List<Album>

)
