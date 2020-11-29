package com.manugmoya.bicimadstations.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob


abstract class CoroutineScopeActivity : AppCompatActivity(), Scope by Scope.Impl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScope()
    }

    override fun onDestroy() {
        cancelScope()
        super.onDestroy()
    }
}