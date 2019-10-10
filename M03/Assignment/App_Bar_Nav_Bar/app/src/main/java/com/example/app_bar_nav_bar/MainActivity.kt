package com.example.app_bar_nav_bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Assigning
        drawerLayout = findViewById(R.id.drawer_layout)
        val tbar = findViewById<View>(R.id.toolbar) as Toolbar
        val toggle = ActionBarDrawerToggle(this, drawerLayout, tbar, R.string.open, R.string.close)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        //setup
        setSupportActionBar(tbar)// I hate androidx
        tbar.title = title
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when (menuItem.itemId) {

                R.id.nav1 -> Toast.makeText(
                    this,
                    "Nav1 implementation later",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.nav2 -> Toast.makeText(
                    this,
                    "Nav2 implementation later",
                    Toast.LENGTH_SHORT
                ).show()


                R.id.nav3 -> Toast.makeText(
                    this,
                    "Nav3 implementation later",
                    Toast.LENGTH_SHORT
                ).show()


            }

            drawerLayout?.closeDrawers()
            true


        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.sel1 ->{Toast.makeText(this, "Sel1 to be implemented", Toast.LENGTH_SHORT).show()}
            R.id.sel2 ->{Toast.makeText(this, "Sel2 to be implemented", Toast.LENGTH_SHORT).show()}
            R.id.sel3 ->{Toast.makeText(this, "Sel3 to be implemented", Toast.LENGTH_SHORT).show()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.selection_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}

