package com.example.apppi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.apppi.data.DbManager
import com.example.apppi.data.FragmentDataObject
import com.example.apppi.ui.main.AboutFragment
import com.example.apppi.ui.main.CustomPresetFragment
import com.example.apppi.ui.main.MainFragment
import com.example.apppi.ui.main.PresetCreatorFragment
import com.google.android.gms.net.CronetProviderInstaller
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var fabPlus: FloatingActionButton
    private lateinit var fabInfo: FloatingActionButton
    private lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        CronetProviderInstaller.installProvider(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commitNow()
        }

        viewPagerAdapter = ViewPagerAdapter(this)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter
        viewPager.visibility = View.GONE

        fabPlus = findViewById(R.id.fabPlus)
        fabPlus.visibility = View.GONE

        fabInfo = findViewById(R.id.fabInfo)
        fabInfo.visibility = View.GONE
    }

    fun navigateToSecondFragment() {
        viewPager.visibility = View.VISIBLE

        fabPlus.visibility = View.VISIBLE
        fabInfo.visibility = View.VISIBLE

        val fragments = DbManager.getInstance(this).getFragments()
        for(fragment in fragments){
            viewPagerAdapter.addFragment(CustomPresetFragment.newInstance(fragment))
        }

        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!)
            .commitNow()
    }

    fun navigateBack(){
        viewPager.adapter = viewPagerAdapter

        viewPager.visibility = View.VISIBLE

        fabPlus.visibility = View.VISIBLE
        fabInfo.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!)
            .commitNow()
    }

    fun navigateBackAndAddFragment(frag : Fragment){
        // adds new fragment to viewPager
        viewPagerAdapter.addFragment(frag)
        navigateBack()
    }

    fun removeThisFragment(frag : Fragment, name : String){
        viewPagerAdapter.removeFragment(frag)
        viewPager.adapter = viewPagerAdapter

        Toast.makeText(this, "Removed ${name} Page", Toast.LENGTH_SHORT).show()
    }

    fun openFragmentBuilder(view : View){
        Log.v("test123","OPEN")
        viewPager.visibility = View.INVISIBLE

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PresetCreatorFragment())
            .addToBackStack(null)
            .commit()

        fabPlus.visibility = View.GONE
        fabInfo.visibility = View.GONE
    }

    fun openAboutPage(view : View){
        viewPager.visibility = View.INVISIBLE

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AboutFragment())
            .addToBackStack(null)
            .commit()

        fabPlus.visibility = View.GONE
        fabInfo.visibility = View.GONE
    }
}