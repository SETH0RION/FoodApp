package com.pe.edu.idat.grupo2.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recetas(
    @SerializedName("idMeal")
    val idMeal: Int,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strCategory")
    val strCategory: String,
    @SerializedName("strArea")
    val strArea: String,
    @SerializedName("strInstructions")
    val strInstructions: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
    val isFavorite:Boolean = false
) :Parcelable {

}
//fun getData():List<Recetas> = listOf()