package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.apppi.MainActivity
import com.example.apppi.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Fragment2 : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var textV: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)

        toolbar = view.findViewById(R.id.toolbar2)
        toolbar.title = "Introductory Fragment"

        textV = view.findViewById(R.id.textView)
        textV.text = "Hello and Welcome to AppPI.\n\nThis application is an Interface for simple API calls and provides some presets.\n" +
                "It is possible to create custom pages by clicking the \"+\"-Button in the bottom right." +
                "\nThe checkbox \"RAW JSON\" enables the raw print of the returned JSON Object.\n By simply providing a JSON Attribute, in the creator," +
                " the software will return the respective value."

        return view
    }

    companion object {
        fun newInstance() = Fragment2()
    }
}