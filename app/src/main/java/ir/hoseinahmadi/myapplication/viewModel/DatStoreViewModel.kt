package ir.hoseinahmadi.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myapplication.data.dataStore.DataStoreRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DatStoreViewModel @Inject constructor(
    private val repository: DataStoreRepositoryImpl
) : ViewModel() {

    companion object {
        const val USER_TOKEN_KEY = "USER_TOKEN_KEY"
        const val USER_ID_KEY = "USER_ID_KEY"
        const val USER_PHONE_KEY = "USER_PHONE_KEY"
        const val USER_NAME_KEY = "USER_NAME_KEY"
        const val USER_PASSWORD_KEY = "USER_PASSWORD_KEY"
        const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
    }


    fun saveUserToken(value: String) {
        viewModelScope.launch {
            repository.putString(USER_TOKEN_KEY, value)
        }
    }

    fun getUserToken(): String? = runBlocking {
        repository.getString(USER_TOKEN_KEY)
    }

    fun saveUserEmail(value: String) {
        viewModelScope.launch {
            repository.putString(USER_EMAIL_KEY, value)
        }
    }

    fun getUserEmail(): String? = runBlocking {
        repository.getString(USER_EMAIL_KEY)
    }


    fun saveUserId(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(USER_ID_KEY, value)
        }
    }

    fun getUserId(): String? = runBlocking {
        repository.getString(USER_ID_KEY)
    }

    fun saveUserPhone(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(USER_PHONE_KEY, value)
        }
    }

    fun getUserPhone(): String? = runBlocking {
        repository.getString(USER_PHONE_KEY)
    }

    fun saveUserPassword(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(USER_PASSWORD_KEY, value)
        }
    }

    fun getUserPassword(): String? = runBlocking {
        repository.getString(USER_PASSWORD_KEY)
    }
    
    fun saveUserName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(USER_NAME_KEY, value)
        }
    }

    fun getUserName(): String? = runBlocking {
        repository.getString(USER_NAME_KEY)
    }



}