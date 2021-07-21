package mbaas.com.nifcloud.kotlinpayloadapp

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import mbaas.com.nifcloud.kotlinpayloadapp.Utils.sendPushWithSearchCondition
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


private const val TIMEOUT = 150000L

@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicationTest {
    private lateinit var device: UiDevice

    @get:Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("mbaas.com.nifcloud.kotlinpayloadapp", appContext.packageName)
    }

    @Test
    fun validateDisplayScreen() {
        onView(allOf(withText("KotlinPayloadApp"), isDisplayed()))
        onView(allOf(withText("com.nifcloud.mbaas.pushId"), isDisplayed()))
        onView(allOf(withText("com.nifcloud.mbaas.richUrl"), isDisplayed()))
        onView(allOf(withText("data (JSON)"), isDisplayed()))
        onView(allOf(withId(R.id.txtPushid), withText("")))
        onView(allOf(withId(R.id.txtRichurl), withText("")))
        onData(allOf(`is`(instanceOf(String.javaClass)), `is`("Key: No key"))).inAdapterView(withId(R.id.lsJson))
    }

    @Test
    @Throws(InterruptedException::class)
    fun clickOnSendNotification() {
        sendPushWithSearchCondition()
        device.openNotification()
        device.wait(Until.hasObject(By.text(NOTIFICATION_TITLE)), TIMEOUT.toLong())
        val title: UiObject2 = device.findObject(By.text(NOTIFICATION_TITLE))
        val text: UiObject2 = device.findObject(By.text(NOTIFICATION_TEXT))
        assertEquals(NOTIFICATION_TITLE, title.text)
        assertEquals(NOTIFICATION_TEXT, text.text)
        title.click()
    }
}