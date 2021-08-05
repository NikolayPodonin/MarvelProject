package com.podonin.marvel_remote_impl.data.modelRemote

import com.google.gson.annotations.SerializedName

data class Items (

	@SerializedName("resourceURI") val resourceURI : String,
	@SerializedName("name") val name : String
)