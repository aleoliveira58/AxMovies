package com.example.axmovies.base

import com.example.axmovies.R
import com.example.axmovies.utils.ErrorUtils
import com.example.axmovies.utils.ResponseApi
import retrofit2.Response


// Aqui esta fazendo a chamada do api
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = safeApiResult(call)

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : ResponseApi {
        try {
            // Está chamando a api
            val response = call.invoke()

            // Esta valindo se a chama teve sucesso ou erro
            return if(response.isSuccessful) {
                ResponseApi.Success(response.body())
            } else {
                val error = ErrorUtils.parseError(response)

                error?.message?.let {  message ->
                    ResponseApi.Error(message)
                } ?: run {
                    ResponseApi.Error(R.string.error_default)
                }
            }
        } catch (error : Exception) {
            return ResponseApi.Error(R.string.error_default)
        }
    }
}