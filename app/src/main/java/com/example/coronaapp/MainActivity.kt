package com.example.coronaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_symptoms)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }
//
//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?): Boolean {
//        super.onCreateOptionsMenu(menu)
//        inflater?.inflate(R.menu.overflow_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return NavigationUI.onNavDestinationSelected(item!!,
//                view!!.findNavController())
//                || super.onOptionsItemSelected(item)
//    }
}