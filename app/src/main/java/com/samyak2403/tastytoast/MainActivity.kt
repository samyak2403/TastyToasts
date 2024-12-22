package com.samyak2403.tastytoast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.samyak2403.tastytoast.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge experience
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view for the activity
        setContentView(binding.root)
    }

    // Toast methods
    fun showSuccessToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "Download Successful!",
            TastyToast.LENGTH_LONG,
            TastyToast.SUCCESS
        )
    }

    fun showWarningToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "Are you sure?",
            TastyToast.LENGTH_LONG,
            TastyToast.WARNING
        )
    }

    fun showErrorToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "Downloading failed! Try again later",
            TastyToast.LENGTH_LONG,
            TastyToast.ERROR
        )
    }

    fun showInfoToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "Searching for username: 'Rahul'",
            TastyToast.LENGTH_LONG,
            TastyToast.INFO
        )
    }

    fun showDefaultToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "This is Default Toast",
            TastyToast.LENGTH_LONG,
            TastyToast.DEFAULT
        )
    }

    fun showConfusingToast(view: View) {
        TastyToast.makeText(
            applicationContext,
            "I don't know!",
            TastyToast.LENGTH_LONG,
            TastyToast.CONFUSING
        )
    }
}
