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
        const val USER_PIP_KEY = "USER_PIP_KEY"
        const val CHECK_LOGIN ="CHECK_LOGIN_KEY"
    }


    fun saveIsLogin(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(CHECK_LOGIN, value)
        }
    }

    fun getIsLogin(): Boolean? = runBlocking {
        repository.getBoolean(CHECK_LOGIN)
    }

    fun saveEnablePip(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_PIP_KEY, value)
        }
    }

    fun getEnablePip(): Boolean? = runBlocking {
        repository.getBoolean(USER_PIP_KEY)
    }



}