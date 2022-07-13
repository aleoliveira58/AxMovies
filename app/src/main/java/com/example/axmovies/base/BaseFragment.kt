package com.example.axmovies.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.axmovies.utils.Command
// Criar essa clase pq pra chamadas api precisa ter o command ai como que for  herda baseFragment vai tem que implemntar o command
abstract class BaseFragment: Fragment() {

    abstract var command : MutableLiveData<Command>
}