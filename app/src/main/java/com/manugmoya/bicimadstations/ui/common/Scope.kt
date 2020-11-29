package com.manugmoya.bicimadstations.ui.common

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob


interface Scope: CoroutineScope {

    var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun cancelScope() {
        job.cancel()
    }

    // Creamos una clase dentro de la propia interface para almacenar el objeto job con su estado
    // Y luego mediante la delegación de Interfaces para no repetir este código
    class Impl : Scope {
        override lateinit var job: Job
    }
}