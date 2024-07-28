package ir.hoseinahmadi.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myapplication.data.model.NotifResponse
import ir.hoseinahmadi.myapplication.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository
):ViewModel() {
    val notifeResponse: MutableStateFlow<NotifResponse> = repository.notifeResponse


    fun senMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.senMessage(message)
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            notifeResponse.emit(NotifResponse())
        }
    }

}