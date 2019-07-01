package spartons.com.prosmssenderapp.activities.main.ui

import android.os.Bundle
import android.view.KeyEvent
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.BaseActivity
import spartons.com.prosmssenderapp.activities.main.fragments.SplashScreenFragment
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.ui.MainScreenFragment
import spartons.com.prosmssenderapp.helper.UiHelper
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class MainActivity : BaseActivity() {

    private companion object {
        private const val FRAGMENT_CONTAINER = R.id.mainActivityRootFragContainer
    }

    private val timer = Timer()
    private val splashScreenFragment = SplashScreenFragment.getInstance()
    private val mainScreenFragment by lazy {
        MainScreenFragment.getInstance()
    }

    private var firstFragmentFlag = true

    @Inject
    lateinit var uiHelper: UiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreenWindow()
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)
        uiHelper.addFragment(splashScreenFragment, supportFragmentManager, FRAGMENT_CONTAINER)
    }

    override fun onResume() {
        super.onResume()
        if (firstFragmentFlag)
            startTimerSchedule(4000)
    }

    private fun startTimerSchedule(delay: Long) {
        timer.schedule(delay) {
            runOnUiThread { clearWindowFlags() }
            uiHelper.replaceFragment(
                mainScreenFragment, supportFragmentManager,
                FRAGMENT_CONTAINER
            )
            firstFragmentFlag = false
        }
    }

    override fun onStop() {
        super.onStop()
        if (firstFragmentFlag) timer.cancel()
    }

    override fun onBackPressed() {}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) = if (keyCode == KeyEvent.KEYCODE_BACK) {
        moveTaskToBack(true)
        true
    } else false

}
