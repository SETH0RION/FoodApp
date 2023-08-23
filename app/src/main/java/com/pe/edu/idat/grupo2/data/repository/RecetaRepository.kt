package com.pe.edu.idat.grupo2.data.repository

import com.pe.edu.idat.grupo2.data.DatosResults
import com.pe.edu.idat.grupo2.data.response.ListaRecetaResponse
import com.pe.edu.idat.grupo2.data.retrofit.RetrofitHelper

class RecetaRepository {
    suspend fun getRecetas():DatosResults<ListaRecetaResponse>{
        return try {
            val response = RetrofitHelper.recetaInstance.getAllRecetas()
            DatosResults.Success(response)
        }catch (e: Exception){
            DatosResults.Error(e)
        }
    }


}