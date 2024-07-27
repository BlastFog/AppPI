package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppi.CronetRequestBuilder
import com.example.apppi.MainActivity

import com.example.apppi.R
import com.example.apppi.data.DbManager
import com.example.apppi.data.FragmentDataObject
import org.chromium.net.CronetEngine


class CustomPresetFragment() : Fragment() {

    private lateinit var fragName : String
    private lateinit var url : String
    private var raw : Boolean = false
    private var key : Boolean = false
    private lateinit var nested : String
    lateinit var resultField : EditText
    lateinit var deleteBut : ImageButton

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

        val titleBar = view.findViewById<TextView>(R.id.apiTitle)
        titleBar.text = fragName.plus(" API")

        val urlField = view.findViewById<TextView>(R.id.url)
        urlField.text = url

        var dropDown = view.findViewById<Spinner>(R.id.spinner)
        if(key) {
            1
            // TODO: Key Implementation
        }else
            dropDown.visibility = View.INVISIBLE


        val fetchBut = view.findViewById<Button>(R.id.customFetchBut)


        dropDown = view.findViewById(R.id.spinner)
        val list : MutableList<String> = ArrayList()
        for(el in DbManager.getInstance(requireContext()).getApis()) {
            list.add(el)
            //list.add(DbManager.getInstance(requireContext()).getApiKey(el))
        }

        dropDown.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)


        fetchBut.setOnClickListener{
            //Log.v("asdasdasdasdas", dropDown.selectedItem.toString())
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
                //queries.put("key","AIzaSyAh0fi44IOxcp9VADCYmiJXwar1GdZJgg4")

                queries.put("key", DbManager.getInstance(requireContext()).getApiKey(dropDown.selectedItem.toString()))
            }

            val queriesUnmutable : Map<String, String> = queries
            Log.v("debugFields", queriesUnmutable.toString())


            //Example URL:  https://www.googleapis.com/youtube/v3/search
            //     params: part=snippet, q=@MrBeast, type=channel
            var myViewModel = ViewModelProvider(this).get(CustomPresetViewModel::class.java)
            resultField = view.findViewById(R.id.customResult)


            if(raw) {
                Log.v("asdasdasdasdasdasd","RAWWWWW")
                CronetRequestBuilder.newInstance().buildRequest(cronetEngine, url, queries, "customRawCall", this)
                myViewModel.jsonObject.observe(context as LifecycleOwner, Observer {
                        jsonObj -> resultField.setText(jsonObj)
                })
            }
            else if(nested.isNotEmpty()){
                Log.v("asdasdasdasdasdasd","NOT RAWWWWW")
                CronetRequestBuilder.newInstance().buildRequest(cronetEngine,url,queries,"customNestedCall:".plus(nested),this)
                myViewModel.jsonAttribute.observe(context as LifecycleOwner, Observer {
                        jsonAtt -> resultField.setText(jsonAtt)
                })
            }
        }

        deleteBut = view.findViewById(R.id.customDeleteBut)

        deleteBut.setOnClickListener{
            val fragment = FragmentDataObject(
                name = fragName,
                url = url,
                raw = raw,
                key = key,
                nested = nested
            )
            DbManager.getInstance(requireContext()).removeFragment(fragment)

            (activity as MainActivity).removeThisFragment(this)
        }

        return view
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

        fun newInstance(fragmentData: FragmentDataObject) : CustomPresetFragment{
            val fragment = CustomPresetFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, fragmentData.name)
            args.putString(ARG_PARAM2, fragmentData.url)
            args.putBoolean(ARG_PARAM3, fragmentData.raw)
            args.putBoolean(ARG_PARAM4, fragmentData.key)
            args.putString(ARG_PARAM5, fragmentData.nested)
            fragment.arguments = args

            return fragment
        }
    }

}