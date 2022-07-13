package com.example.axmovies.utils


// Classe criada para tratar oque foi sucesso ou erro
sealed class ResponseApi{

    class Success(var data: Any?) : ResponseApi()
    class Error(val message: Int) : ResponseApi()

}
