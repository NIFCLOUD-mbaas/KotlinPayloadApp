package mbaas.com.nifcloud.kotlinpayloadapp

import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.nifcloud.mbaas.core.NCMBFirebaseMessagingService
import org.json.JSONException
import org.json.JSONObject


class CustomFcmListenerService : NCMBFirebaseMessagingService() {
    private val TAG = "FcmService"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data != null) {
            val data = Bundle()
            val d = remoteMessage.data
            for (key in d.keys) {
                data.putString(key, d[key])
            }

            //ペイロードデータの取得
            if (data.containsKey("com.nifcloud.mbaas.Data")) {
                try {
                    val json = JSONObject(data.getString("com.nifcloud.mbaas.Data"))
                } catch (e: JSONException) {
                    //エラー処理
                }

            } else if (data.containsKey("com.nifcloud.mbaas.PushId")) {
                val pushid = data.getString("com.nifcloud.mbaas.PushId")
                Log.d(TAG, "" + pushid)
            } else if (data.containsKey("com.nifcloud.mbaas.RichUrl")) {
                val url = data.getString("com.nifcloud.mbaas.RichUrl")
                Log.d(TAG, "" + url)
            }
        }
        //デフォルトの通知
        super.onMessageReceived(remoteMessage)
    }

}
