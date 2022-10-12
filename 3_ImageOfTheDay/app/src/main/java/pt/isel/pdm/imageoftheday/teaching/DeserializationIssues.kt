package pt.isel.pdm.imageoftheday.teaching

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class ResponseBase<T>(
    val success: Boolean,
    val code: Int,
    val data: T
) {
    companion object {
        inline fun <reified T> getType(): TypeToken<ResponseBase<T>> =
            object : TypeToken<ResponseBase<T>>() {}

    }
}

data class LoginData(
    val token: String,
    val expires: Long
)

data class UserData(
    val userName: String,
    val pictureUrl: String
)

typealias LoginResponse = ResponseBase<LoginData>
typealias UserDataResponse = ResponseBase<UserData>

fun test1() {
    var loginResponse = LoginResponse(true, 123, LoginData("#$123", 123L))
    var dataResponse = ResponseBase<UserData>(true, 345, UserData("user", "https://pic.com"))

    val loginJson = Gson().toJson(loginResponse);
    val userDataJson = Gson().toJson(dataResponse);


    val loginResponseDeser = Gson().fromJson<LoginResponse>(loginJson, ResponseBase.getType<LoginData>().type)
    val userDataDeser = Gson().fromJson<ResponseBase<UserData>>(loginJson, ResponseBase.getType<UserData>().type)

    Log.d("", loginResponseDeser.code.toString())
    Log.d("", loginResponseDeser.data.token)
}


fun test1_doesntwork() {
    var loginResponse = LoginResponse(true, 123, LoginData("#$123", 123L))
    var dataResponse = ResponseBase<UserData>(true, 345, UserData("user", "https://pic.com"))

    val loginJson = Gson().toJson(loginResponse);
    val userDataJson = Gson().toJson(dataResponse);

    val loginResponseDeser = Gson().fromJson<LoginResponse>(loginJson, ResponseBase::class.java)
    val userDataDeser = Gson().fromJson<ResponseBase<UserData>>(loginJson, ResponseBase::class.java)

    Log.d("", loginResponseDeser.code.toString())
    Log.d("", loginResponseDeser.data.token)

}