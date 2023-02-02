package com.davinciapps.fridgemaster.data.util

/* 참고용 */
sealed class RepositoryResult<T>(
    val data: T? = null,
    val resultCode: String = "",
    val resultMessage: String? = null,
    val isSuccessful: Boolean = true
) {
    class Success<T>(data: T, resultCode: String, resultMessage: String) : RepositoryResult<T>(data, resultCode = resultCode, resultMessage = resultMessage, isSuccessful = true)
    class Loading<T>(data: T? = null) : RepositoryResult<T>(data, "", null, false)
    class Error<T>(errorCode: String, errorMessage: String, data: T? = null) : RepositoryResult<T>(data, errorCode, errorMessage, isSuccessful = false)
}
