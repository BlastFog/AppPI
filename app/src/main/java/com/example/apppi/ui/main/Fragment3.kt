package com.example.apppi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apppi.R


class Fragment3 : Fragment() {
// Youtube API Interaction Fragment (Auth Token)
// Order: 1.  GET https://www.googleapis.com/youtube/v3/search params: part=snippet, q=@..., type=channel, key=...
// 2. GET https://www.googleapis.com/youtube/v3/channels params: part=statistics, id=oldQuery[items[statistics]].subcriberCount, key=...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false)
    }

    companion object {
        fun newInstance() = Fragment3()
    }
}