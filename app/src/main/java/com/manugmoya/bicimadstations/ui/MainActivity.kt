package com.manugmoya.bicimadstations.ui

import android.content.Intent
import android.os.Bundle
import com.manugmoya.bicimadstations.common.CoroutineScopeActivity
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.EMAIL
import com.manugmoya.bicimadstations.model.PASSWORD
import com.manugmoya.bicimadstations.model.StationsDb
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch {
            val tokenResponse = StationsDb.service.getToken(EMAIL,PASSWORD)
            tokenResponse.data?.get(0)?.accessToken?.let { token ->
                val stations = StationsDb.service.getStation(token)
                binding.rvStations.adapter = StationsAdapter(stations.data){ station ->
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("Station",station)
                    startActivity(intent)
                }
            }
        }
    }
}