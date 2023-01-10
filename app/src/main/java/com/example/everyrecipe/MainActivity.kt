package com.example.everyrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelPagination
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.Recipe
import com.amplifyframework.kotlin.core.Amplify
import com.example.everyrecipe.data.model.Food
import com.example.everyrecipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
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