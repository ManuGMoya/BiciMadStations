package com.manugmoya.bicimadstations.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.databinding.ActivityMainBinding
import com.manugmoya.bicimadstations.model.FakeData

class MainActivity : AppCompatActivity() {

    val stationList = listOf(
            FakeData("Puerta del Sol A", "Puerta del Sol nº 1"),
            FakeData("Miguel Moya", "Calle Miguel Moya nº 1"),
            FakeData("Plaza Conde Suchil", "Plaza del Conde Suchil nº 2-4"),
            FakeData("Malasaña", "Calle Manuela Malasaña nº 5"),
            FakeData("Fuencarral", "Calle Fuencarral nº 108"),
            FakeData("Colegio ArquitectosA", "Calle Hortaleza nº 63"),
            FakeData("Hortaleza", "Calle Hortaleza nº 75"),
            FakeData("Alonso Martínez", "Plaza de Alonso Martínez nº 5"),
            FakeData("Plaza de San Miguel", "Plaza de San Miguel nº 9"),
            FakeData("Marqués de la Ensenada", "Calle Marqués de la Ensenada nº 16"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStations.adapter = StationsAdapter(stationList)
    }
}