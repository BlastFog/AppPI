package com.example.apppi

import android.util.Log
import com.example.apppi.ui.main.Fragment2

class QueryStringBuilder {

    fun buildQueryString(apiURL: String, queries: Map<String, String>): String {
        var erg: String = apiURL
        erg = erg.plus("?")

        queries.forEach {
            entry -> erg = erg.plus(entry.key+"="+entry.value+"&");
        }

        return erg.substring(0,erg.length-1)
    }

    companion object {
        fun newInstance() = QueryStringBuilder()
    }

}