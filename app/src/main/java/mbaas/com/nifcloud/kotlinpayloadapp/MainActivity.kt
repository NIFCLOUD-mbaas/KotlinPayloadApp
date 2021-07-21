package mbaas.com.nifcloud.kotlinpayloadapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.nifcloud.mbaas.core.NCMB
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var _pushId: TextView
    lateinit var _richurl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY")

        try {
            val tmpBlank = JSONObject("{'No key':'No value'}")
            val lv = findViewById<ListView>(R.id.lsJson) as ListView
            if (lv != null) {
                lv.adapter = ListAdapter(this, tmpBlank)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    public override fun onResume() {
        super.onResume()
        //**************** ペイロード、リッチプッシュを処理する ***************
        val intent = intent

        //プッシュ通知IDを表示
        _pushId = findViewById<TextView>(R.id.txtPushid)
        val pushid = intent.getStringExtra("com.nifcloud.mbaas.PushId")
        _pushId.text = pushid

        //RichURLを表示
        _richurl = findViewById<TextView>(R.id.txtRichurl)
        val richurl = intent.getStringExtra("com.nifcloud.mbaas.RichUrl")
        _richurl.text = richurl

        //プッシュ通知のペイロードを表示
        if (intent.getStringExtra("com.nifcloud.mbaas.Data") != null) {
            try {
                val json = JSONObject(intent.getStringExtra("com.nifcloud.mbaas.Data"))
                if (json != null) {
                    val lv = findViewById<View>(R.id.lsJson) as ListView
                    lv.adapter = ListAdapter(this, json)
                }
            } catch (e: JSONException) {
                //エラー処理
            }

        }
        intent.removeExtra("com.nifcloud.mbaas.RichUrl")
    }
}
