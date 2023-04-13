package com.busramestan.project.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.busramestan.project.R
import com.busramestan.project.databinding.ActivityHamburgerBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.nav_header_hamburger.*


class HamburgerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHamburgerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHamburgerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val header: View = binding.navView.getHeaderView(0)
        val mylogout = header.findViewById<View>(R.id.logoutButton) as Button

        mylogout.setOnClickListener {
            val settings: SharedPreferences =
                this.getSharedPreferences("Project", Context.MODE_PRIVATE)
            settings.edit().clear().commit()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        setSupportActionBar(binding.appBarHamburger.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listFragment, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hamburger, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}