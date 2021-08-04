package com.podonin.marvel_api.data.model

import com.google.gson.annotations.SerializedName

data class MarvelCharactersResponse (

	@SerializedName("offset") val offset : Int,
	@SerializedName("limit") val limit : Int,
	@SerializedName("total") val total : Int,
	@SerializedName("count") val count : Int,
	@SerializedName("results") val results : List<MarvelCharacter>
)