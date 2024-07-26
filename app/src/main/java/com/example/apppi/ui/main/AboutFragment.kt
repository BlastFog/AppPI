package com.example.apppi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apppi.MainActivity
import com.example.apppi.R

class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        (activity as MainActivity).navigateBack()

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    fun navigateToGitHub(view: View) {
        val visitGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://https://github.com/BlastFog/AppPI/"))
        startActivity(visitGithub)
    }

}