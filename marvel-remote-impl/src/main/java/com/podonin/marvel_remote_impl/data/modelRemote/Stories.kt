package com.podonin.marvel_remote_impl.data.modelRemote

import com.google.gson.annotations.SerializedName

data class Stories (

	@SerializedName("available") val available : Int,
	@SerializedName("collectionURI") val collectionURI : String,
	@SerializedName("items") val items : List<Items>,
	@SerializedName("returned") val returned : Int
)