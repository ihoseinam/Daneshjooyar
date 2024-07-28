package ir.hoseinahmadi.myapplication.data.remote

import ir.hoseinahmadi.myapplication.data.model.NotifResponse
import ir.hoseinahmadi.myapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SendMessageApiInterFace {

    @GET("send")
    suspend fun sendMessage(
        @Query("to") token: String = Constants.TOKEN,
        @Query("text") text: String
    ):Response<NotifResponse>

}