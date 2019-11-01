package spartons.com.prosmssenderapp.activities.main.fragments

import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.enums.TypeFaceEnum
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.util.getTypeface

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class SplashScreenFragment : BaseFragment() {

    companion object {
        fun getInstance() = SplashScreenFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_splash_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        splashScreenLogoImageView.setImageResource(R.drawable.pro_sms_sender_logo)
        requireActivity().getTypeface(TypeFaceEnum.WELCOME_MESSAGE_TYPEFACE).also {
            splashScreenWelcomeMessageTextView.typeface = it
        }
    }

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
    }
}
