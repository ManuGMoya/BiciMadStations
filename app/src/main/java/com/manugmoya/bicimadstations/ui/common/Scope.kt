package com.manugmoya.bicimadstations.ui.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


interface Scope: CoroutineScope {

    var job: Job
    val uiDispatcher: CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = uiDispatcher + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun cancelScope() {
        job.cancel()
    }

    class Impl(override val uiDispatcher: CoroutineDispatcher) : Scope {
        override lateinit var job: Job
    }
}