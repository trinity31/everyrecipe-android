package com.example.everyrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.Recipe
import com.amplifyframework.kotlin.core.Amplify
import com.example.everyrecipe.data.model.Food

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private suspend fun getRecipe(id: String) {
        try {
            val response = Amplify.API.query(ModelQuery.get(Recipe::class.java, id))
            Log.i("MyAmplifyApp", response.data.name)
        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Query failed", error)
        }
    }
}