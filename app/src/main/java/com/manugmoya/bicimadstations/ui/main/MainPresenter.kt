package com.manugmoya.bicimadstations.ui.main

import android.view.View
import android.widget.Toast
import com.manugmoya.bicimadstations.ui.common.orderListByLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter {

    // 3 - necesitamos uyna propiedad de tipo View que será nullable, esto tiene que ver con el ciclo
    // de vida de la vista y si esta es destruida si intentamos actualizar la UI, nos lanzará una excepción
    // Lo que haremos para evitar esto en el onDestroy vaciar esta property view.
    private var view: View? = null

    // 1 - El presenter necesita alguna manera de comunicarse con la vista que sera mediante una interface
    // Esta interface tenemos que implementarla en la vista MainActivity
    interface View {

    }

    // 2 - Este View hace referencia a la interface declarada arriba
    fun onCreate(view: View) {
        this.view = view

    }


    // 4 - Asociado al ciclo de vida de la vista, destruimos la instancia view haciéndola nullable
    fun onDestroy() {
        this.view = null
    }

}