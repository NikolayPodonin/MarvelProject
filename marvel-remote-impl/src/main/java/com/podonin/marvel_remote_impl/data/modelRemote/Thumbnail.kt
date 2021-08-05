package com.podonin.marvel_remote_impl.data.modelRemote

import com.google.gson.annotations.SerializedName

data class Thumbnail (

	@SerializedName("path") val path : String,
	@SerializedName("extension") val extension : String
)