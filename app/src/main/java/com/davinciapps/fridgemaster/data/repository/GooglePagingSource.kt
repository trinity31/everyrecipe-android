package com.davinciapps.fridgemaster.data.repository

import android.util.Log
import com.davinciapps.fridgemaster.data.param.res.google.RecipeItem
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davinciapps.fridgemaster.BuildConfig
import com.davinciapps.fridgemaster.data.api.GoogleAPIService
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.model.RecipeInfo
import retrofit2.HttpException
import java.io.IOException

private const val GOOGLE_STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 10

class GooglePagingSource(
    private val service: GoogleAPIService,
    private val query: String
): PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: GOOGLE_STARTING_PAGE_INDEX
        return try {
            val response = service.getSearchResult(
                query,
                BuildConfig.GOOGLE_SEARCH_ENGINE,
                BuildConfig.GOOGLE_API_KEY,
                "countryKR",
                "레시피",
                1,
                "lang_ko",
                position,
                params.loadSize
            )

            Log.i("GooglePagingSource", "params.loadSize: ${params.loadSize}")

            val repos = response.body()?.items?.map {
                val b = Recipe(
                    0,
                    RecipeInfo(
                        name = it.title,
                        description = it.snippet,
                        imageUrl = it.pagemap.cse_image.firstOrNull()?.src ?: "",
                        thumbnailImage = it.pagemap.cse_thumbnail.firstOrNull()?.src ?: "",
                        link = it.link
                    ),
                    ingredients = listOf()
                )
                b
            } ?: emptyList()

            Log.i("GooglePagingSource", "repos: ${repos}")

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            Log.i("GooglePagingSource", "nextKey: ${nextKey}")
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GOOGLE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}