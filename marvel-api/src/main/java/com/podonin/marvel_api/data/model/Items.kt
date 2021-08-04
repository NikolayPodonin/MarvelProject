package com.podonin.marvel_api.data.model

import com.google.gson.annotations.SerializedName

data class Items (

	@SerializedName("resourceURI") val resourceURI : String,
	@SerializedName("name") val name : String
)