package com.example.team11


import android.app.Activity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.team11.uiAndViewModels.bottomNavigation.MainActivity
import com.example.team11.uiAndViewModels.place.PlaceActivity
import com.example.team11.uiAndViewModels.placesList.PlacesListFragment
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PlaceActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragmentInContainer<PlacesListFragment>(themeResId = R.style.AppTheme)
    }



    @Test
    fun testBackButton() {
        goToPlaceActivity()
        val activityInstance = getActivityInstance() as PlaceActivity
        onView(withId(R.id.buttonBack))
            .perform(click())
        assertTrue(activityInstance.isFinishing)
    }

    @Test
    fun testPlaceName() {
        goToPlaceActivity()
        val activityInstance = getActivityInstance() as PlaceActivity
        val placeInstance = activityInstance.viewModel.place
        onView(withId(R.id.textPlaceName)).check(matches(withText(placeInstance!!.value!!.name)))
    }

    @Test
    fun testWaterTemp(){
        goToPlaceActivity()
        val activityInstance = getActivityInstance() as PlaceActivity
        val placeInstance = activityInstance.viewModel.place

        if (placeInstance!!.value!!.tempWater == Int.MAX_VALUE) {
            onView(withId(R.id.textTempWater))
                .check(matches(withText(R.string.no_data)))
        } else {
            onView(withId(R.id.textTempWater))
                .check(matches(withSubstring(placeInstance.value!!.tempWater.toString())))
        }
    }

    @Test
    fun testOpenCurrentsInfo(){
        goToPlaceActivity()
        onView(withId(R.id.buttonCurrentsInfo))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.layoutCurrentsInfo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCloseCurrentsInfo() {
        //knapp
        goToPlaceActivity()
        onView(withId(R.id.buttonCurrentsInfo))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.buttonCurrentsCloseInfo))
            .perform(click())
        onView(withId(R.id.layoutCurrentsInfo)).check(matches(not(isDisplayed())))

        //klikk utenfor layout
        onView(withId(R.id.buttonCurrentsInfo))
            .perform(click())
        onView(withId(R.id.layoutInScrollView))
            .perform(click())
        onView(withId(R.id.layoutCurrentsInfo)).check(matches(not(isDisplayed())))
    }

   @Test
    fun testOpenUVInfo(){
        goToPlaceActivity()
        onView(withId(R.id.buttonUVInfo))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.layoutUVInfo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCloseUVInfo() {
        //knapp
        goToPlaceActivity()
        onView(withId(R.id.buttonUVInfo))
            .perform(scrollTo())
            .perform(click())
        onView(withId(R.id.buttonUVCloseInfo))
            .perform(click())
        onView(withId(R.id.layoutUVInfo)).check(matches(not(isDisplayed())))

        //klikk utenfor layout
        onView(withId(R.id.buttonUVInfo))
            .perform(click())
        onView(withId(R.id.layoutInScrollView))
            .perform(click())
        onView(withId(R.id.layoutUVInfo)).check(matches(not(isDisplayed())))
    }




    private fun goToPlaceActivity() {
        onView(allOf(withId(R.id.recycler_viewPlaces), isDisplayed())).perform(click())
    }

    private fun getActivityInstance(): Activity? {
        val currentActivity = arrayOf<Activity?>(null)
        getInstrumentation().runOnMainSync(Runnable {
            val resumedActivity =
                ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
            val it: Iterator<Activity> = resumedActivity.iterator()
            currentActivity[0] = it.next()
        })
        return currentActivity[0]
    }
}