package spartons.com.prosmssenderapp.activities.main.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.enums.TypeFaceEnum
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.helper.UiHelper
import javax.inject.Inject

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class SplashScreenFragment : BaseFragment() {

    companion object {
        fun getInstance() = SplashScreenFragment()
    }

    private lateinit var splashScreenLogoImageView: ImageView
    private lateinit var splashScreenWelcomeMessageTextView: TextView

    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var uiHelper: UiHelper

    override fun getLayoutResId() = R.layout.fragment_splash_screen

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
        fragmentComponent.inject(this)
        splashScreenLogoImageView = mRootView.findViewById(R.id.splashScreenLogoImageView)
        splashScreenWelcomeMessageTextView = mRootView.findViewById(R.id.splashScreenWelcomeMessageTextView)
        picasso.load(R.drawable.pro_sms_sender_logo).into(splashScreenLogoImageView)
        val typeface = uiHelper.getTypeFace(TypeFaceEnum.WELCOME_MESSAGE_TYPEFACE)
        splashScreenWelcomeMessageTextView.typeface = typeface
    }
}
