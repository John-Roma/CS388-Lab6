package com.codepath.lab6

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codepath.lab6.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // apply saved theme before views load
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        applyTheme(prefs.getBoolean(KEY_DARK_MODE, false))

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val parksFragment = ParksFragment()
        val campgroundFragment = CampgroundFragment()
        val settingsFragment = SettingsFragment()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_parks -> parksFragment
                R.id.nav_campgrounds -> campgroundFragment
                R.id.nav_settings -> settingsFragment
                else -> homeFragment
            }
            replaceFragment(fragment)
            true
        }

        // only set default on first launch, so a theme switch keeps you on Settings
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }
}