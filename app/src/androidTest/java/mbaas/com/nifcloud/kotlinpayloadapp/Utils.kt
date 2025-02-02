package mbaas.com.nifcloud.kotlinpayloadapp

import android.os.Build
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import com.nifcloud.mbaas.core.NCMBInstallation
import com.nifcloud.mbaas.core.NCMBPush
import com.nifcloud.mbaas.core.NCMBQuery
import org.json.JSONArray
import org.json.JSONException

private const val TAG = "FcmService"
const val NOTIFICATION_TITLE = "UITest push notification"
const val NOTIFICATION_TEXT =
    "Thank you! We appreciate your business, and we’ll do our best to continue to give you the kind of service you deserve."
const val NOTIFICATION_RICH_URL = "https://www.nifcloud.com/"

object Utils {
    fun sendPushWithSearchCondition() {
        val installation = NCMBInstallation.getCurrentInstallation()
        installation.getDeviceTokenInBackground { token, e ->
            val query = NCMBQuery<NCMBInstallation>("installation")
            query.whereEqualTo("deviceToken", token)
            val push = NCMBPush()
            push.setSearchCondition(query)
            push.title = NOTIFICATION_TITLE
            push.message = NOTIFICATION_TEXT
            push.richUrl = NOTIFICATION_RICH_URL
            try {
                push.target = JSONArray("[android]")
            } catch (jsonException: JSONException) {
                jsonException.printStackTrace()
            }
            push.dialog = true
            push.sendInBackground { e ->
                if (e != null) {
                    Log.d(TAG, "Send push fail")
                } else {
                    Log.d(TAG, "Send push success!")
                }
            }
        }
    }

    internal fun allowPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33) {
            val device = UiDevice.getInstance(getInstrumentation())
            val allowPermissions = device.findObject(UiSelector().text("Allow"))
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click()
                } catch (e: UiObjectNotFoundException) {
                    Log.d(TAG, "Error: " + e.message)
                }
            }
        }
    }
}