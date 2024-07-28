package ir.hoseinahmadi.myapplication.repository

import ir.hoseinahmadi.myapplication.data.model.NotifResponse
import ir.hoseinahmadi.myapplication.data.remote.SendMessageApiInterFace
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val apiInterFace: SendMessageApiInterFace
){

    val notifeResponse = MutableStateFlow(NotifResponse())

    suspend fun senMessage(message:String){
        val response =try {
            apiInterFace.sendMessage(text =message)
        }catch (e:Exception){
            return
        }
        if (response.isSuccessful){
            response.body()?.let {
                notifeResponse.emit(it)
            }
        }
    }
}