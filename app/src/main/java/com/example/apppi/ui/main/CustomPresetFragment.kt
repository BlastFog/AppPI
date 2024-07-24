package com.example.apppi.ui.main

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppi.CronetRequestBuilder

import com.example.apppi.R
import org.chromium.net.CronetEngine


class CustomPresetFragment() : Fragment() {

    private lateinit var fragName : String
    private lateinit var url : String
    private var raw : Boolean = false
    private var key : Boolean = false
    private lateinit var nested : String
    lateinit var resultField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            fragName = it.getString(ARG_PARAM1).toString()
            url = it.getString(ARG_PARAM2).toString()
            raw = it.getBoolean(ARG_PARAM3)
            key = it.getBoolean(ARG_PARAM4)
            nested = it.getString(ARG_PARAM5).toString()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_custom, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.customToolbar)
        toolbar.title = fragName.plus(" API")

        val urlField = view.findViewById<TextView>(R.id.url)
        urlField.text = url

        val dropDown = view.findViewById<Spinner>(R.id.spinner)
        if(key) {
            1
            // TODO: Key Implementation
        }else
            dropDown.visibility = View.INVISIBLE


        val fetchBut = view.findViewById<Button>(R.id.customFetchBut)
        fetchBut.setOnClickListener{

            val myBuilder = CronetEngine.Builder(context)
            val cronetEngine: CronetEngine = myBuilder.build()

            val queries : MutableMap<String, String> = mutableMapOf()

            checkKeyValuePair(R.id.customKey1, R.id.customValue1, view, queries)
            checkKeyValuePair(R.id.customKey2, R.id.customValue2, view, queries)
            checkKeyValuePair(R.id.customKey3, R.id.customValue3, view, queries)
            checkKeyValuePair(R.id.customKey4, R.id.customValue4, view, queries)
            checkKeyValuePair(R.id.customKey5, R.id.customValue5, view, queries)
            if(key){
                // TODO: get Key from dropdown and keyStorage
                queries.put("key","AIzaSyAh0fi44IOxcp9VADCYmiJXwar1GdZJgg4")
            }

            val queriesUnmutable : Map<String, String> = queries
            Log.v("debugFields", queriesUnmutable.toString())


            //Example URL:  https://www.googleapis.com/youtube/v3/search
            //     params: part=snippet, q=@MrBeast, type=channel

            if(raw)
                CronetRequestBuilder.newInstance().buildRequest(cronetEngine,url,queries,"customRawCall",this)


            var myViewModel = ViewModelProvider(this).get(CustomPresetViewModel::class.java)
            resultField = view.findViewById(R.id.customResult)

            myViewModel.jsonObject.observe(context as LifecycleOwner, Observer {
                    jsonObj -> resultField.setText(jsonObj)
            })

        }

        return view
    }

    fun observedChange(jsonObj : String, cronetEngine : CronetEngine, resultField : EditText){

    }

    fun checkKeyValuePair(cKey : Int, cValue : Int, view : View, queries : MutableMap<String, String>){
        val keyField = view.findViewById<EditText>(cKey)
        if(keyField.text.isNotEmpty()){
            val valueField = view.findViewById<EditText>(cValue)
            queries.put(keyField.text.toString(), valueField.text.toString())
        }
    }

    companion object {
        private const val ARG_PARAM1 = "name"
        private const val ARG_PARAM2 = "url"
        private const val ARG_PARAM3 = "raw"
        private const val ARG_PARAM4 = "key"
        private const val ARG_PARAM5 = "nested"

        fun newInstance(name : String, url : String, raw : Boolean, key : Boolean, nested : String) : CustomPresetFragment{
            val fragment = CustomPresetFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, name)
            args.putString(ARG_PARAM2, url)
            args.putBoolean(ARG_PARAM3, raw)
            args.putBoolean(ARG_PARAM4, key)
            args.putString(ARG_PARAM5, nested)
            fragment.arguments = args

            return fragment
        }
    }

}