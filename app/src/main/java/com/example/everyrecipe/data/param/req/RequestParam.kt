package com.example.everyrecipe.data.param.req

import com.google.gson.Gson

open class RequestParam {
    private val requestParams = hashMapOf<String, Any>()

    fun addParam(key: String, value: Any) {
        requestParams[key] = value
    }

    fun addParamChildArray(key: String, valueChildArray: List<String>): MutableList<HashMap<String, String>> {
        val requestParamsChildArray = mutableListOf<HashMap<String, String>>()

        valueChildArray.forEach {
            val map = HashMap<String, String>()
            map[key] = it
            requestParamsChildArray.add(map)
        }

        return requestParamsChildArray
    }

    fun getParamMap(): HashMap<String, Any> {
        return requestParams
    }

    fun getParamJsonObj(): String {
        return Gson().toJsonTree(requestParams).asJsonObject.toString()
    }
}