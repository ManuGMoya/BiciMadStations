package com.manugmoya.bicimadstations.ui

import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.manugmoya.bicimadstations.R
import com.manugmoya.bicimadstations.data.server.StationsDb
import com.manugmoya.bicimadstations.ui.main.MainActivity
import com.manugmoya.bicimadstations.utils.fromJson
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.get
import java.util.*

class UiTest : KoinTest {

    private val mockWebServerRule = MockWebServerRule()

    @get: Rule
    val testRule: RuleChain = RuleChain.outerRule(mockWebServerRule)
        .around(
            GrantPermissionRule.grant(
                "android.permission.ACCESS_FINE_LOCATION"
            )
        )
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("token.json")
        )
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("stations.json")
        )
        val resource = OkHttp3IdlingResource.create("OkHttp", get<StationsDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAStationNavigatesToDetail() {
        val language = Locale.getDefault().language

        if (!isGpsEnabled()) {
            tapTurnOffGpsBtn()
        }

        runBlocking {
            // this delay is to wait orderListByLocation function finish.
            delay(3000)
        }

        Espresso.onView(withId(R.id.rv_stations)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                ViewActions.click()
            )
        )
        // Check language
        if (language == "es") {
            Espresso.onView(withId(R.id.stationDetailInfo))
                .check(ViewAssertions.matches(ViewMatchers.withText(containsString("Dirección"))))
        } else {
            Espresso.onView(withId(R.id.stationDetailInfo))
                .check(ViewAssertions.matches(ViewMatchers.withText(containsString("Address"))))
        }

    }
}

private fun isGpsEnabled(): Boolean {
    val context = getInstrumentation().targetContext.applicationContext
    val manager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

@Throws(Exception::class)
private fun tapTurnOffGpsBtn() {
    val u = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    val denyGpsBtn: UiObject = u.findObject(
        UiSelector()
            .className("android.widget.Button").packageName("com.google.android.gms")
            .resourceId("android:id/button2")
            .clickable(true).checkable(false)
    )
    u.pressDelete()
    if (denyGpsBtn.exists() && denyGpsBtn.isEnabled) {
        do {
            denyGpsBtn.click()
        } while (denyGpsBtn.exists())
    }
}
