package app.yournewsontime.data.model

import com.google.gson.annotations.SerializedName

data class Multimedia(
    @SerializedName("url") val url: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("width") val width: Int?,
    @SerializedName("subtype") val subtype: String?,
    @SerializedName("legacy") val legacy: Map<String, String>?
)