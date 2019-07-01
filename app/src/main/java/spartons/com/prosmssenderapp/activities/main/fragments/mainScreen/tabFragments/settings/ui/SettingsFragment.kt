package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.viewModel.SettingsFragmentViewModel
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_MESSAGE_DELAY_SECONDS
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_STORE_MESSAGE_HISTORY_FLAG
import spartons.com.prosmssenderapp.helper.UiHelper
import spartons.com.prosmssenderapp.util.ViewModelFactory
import spartons.com.prosmssenderapp.util.getMutedColor
import spartons.com.prosmssenderapp.util.getResourceString
import javax.inject.Inject

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    @Inject
    lateinit var uiHelper: UiHelper

    private lateinit var bulkSmsSettingNoteTextView: TextView
    private lateinit var bulkSmsSettingDelaySmsDecrementTextView: TextView
    private lateinit var bulkSmsSettingDelayTextView: TextView
    private lateinit var bulkSmsSettingDelaySmsErrorTextView: TextView
    private lateinit var bulkSmsSettingDelaySmsIncrementTextView: TextView
    private lateinit var bulkSmsSettingDeleteAllSmsButton: MaterialButton
    private lateinit var bulkSmsSettingStoreHistoryCheckBox: MaterialCheckBox
    private lateinit var bulkSmsSettingShowAdButton: MaterialButton

    private lateinit var viewModel: SettingsFragmentViewModel

    companion object {

        private const val LEAST_MINIMUM_SMS_DELAY_VALUE = 2

        fun getInstance() =
            SettingsFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_settings

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
        /**
         * Injecting the current setting fragment to dependency graph so that we can inject our models.
         */

        fragmentComponent.inject(this)
        viewModel = ViewModelProviders.of(this, factory)[SettingsFragmentViewModel::class.java]

        initViews(mRootView)

        /**
         * Creating multicolor Note text with the help SpannableStringBuilder.
         */

        val spanBuilder = SpannableStringBuilder()
            .color(getMutedColor(R.color.colorRed)) {
                this.bold {
                    append(getResourceString(R.string.note_colon))
                }
            }
            .color(getMutedColor(R.color.colorPrimaryText)) {
                append("  ".plus(getResourceString(R.string.avoid_blocking_your_sim_content)))
            }

        /**
         * Setting the spans to NoteTextView
         */

        bulkSmsSettingNoteTextView.setText(spanBuilder, TextView.BufferType.SPANNABLE)

        /**
         * Decrement the delay sms in seconds if the current delay greater than 2 seconds.
         */

        bulkSmsSettingDelaySmsDecrementTextView.setOnClickListener {
            var delayValue = bulkSmsSettingDelayTextView.text.toString().toInt()
            if (delayValue <= LEAST_MINIMUM_SMS_DELAY_VALUE) {
                bulkSmsSettingDelaySmsErrorTextView.text =
                    getResourceString(R.string.delay_must_be_greater_than_two_seconds)
                bulkSmsSettingDelaySmsErrorTextView.visibility = VISIBLE
                return@setOnClickListener
            }
            bulkSmsSettingDelayTextView.text = (--delayValue).toString()
            if (bulkSmsSettingDelaySmsErrorTextView.isVisible)
                bulkSmsSettingDelaySmsErrorTextView.visibility = GONE
            sharedPreferenceHelper.putInt(BULK_SMS_MESSAGE_DELAY_SECONDS, delayValue)
        }

        /**
         * Increment the delay sms in seconds.
         */

        bulkSmsSettingDelaySmsIncrementTextView.setOnClickListener {
            var delayValue = bulkSmsSettingDelayTextView.text.toString().toInt()
            bulkSmsSettingDelayTextView.text = (++delayValue).toString()
            if (bulkSmsSettingDelaySmsErrorTextView.isVisible)
                bulkSmsSettingDelaySmsErrorTextView.visibility = GONE
            sharedPreferenceHelper.putInt(BULK_SMS_MESSAGE_DELAY_SECONDS, delayValue)
        }

        /**
         * Delete all bulk sms history from SQLite database.
         */

        bulkSmsSettingDeleteAllSmsButton.setOnClickListener {
            viewModel.deleteAllBulkSms()
            uiHelper.showSnackBar(mRootView, getResourceString(R.string.all_sms_history_deleted_successfully))
        }

        /**
         * Getting the current message store history flag from shared preferences and apply the value to check box.
         */

        val messageHistoryFlag = sharedPreferenceHelper.getBoolean(BULK_SMS_STORE_MESSAGE_HISTORY_FLAG)
        bulkSmsSettingStoreHistoryCheckBox.isChecked = messageHistoryFlag

        /**
         * Applying the check change listener to message history check box and store the selected inside the shared preference.
         */

        bulkSmsSettingStoreHistoryCheckBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferenceHelper.putBoolean(BULK_SMS_STORE_MESSAGE_HISTORY_FLAG, isChecked)
        }

        /**
         * Retrieving the current delay sms from shared preference and setting it.
         */

        val delayValue = sharedPreferenceHelper.getInt(BULK_SMS_MESSAGE_DELAY_SECONDS)
        bulkSmsSettingDelayTextView.text = delayValue.toString()

        bulkSmsSettingShowAdButton.setOnClickListener {
            uiHelper.showSnackBar(mRootView, getResourceString(R.string.feature_not_added_yet))
        }
    }

    private fun initViews(view: View) {
        bulkSmsSettingNoteTextView = view.findViewById(R.id.bulkSmsSettingNoteTextView)
        bulkSmsSettingDelaySmsDecrementTextView = view.findViewById(R.id.bulkSmsSettingDelaySmsDecrementTextView)
        bulkSmsSettingDelayTextView = view.findViewById(R.id.bulkSmsSettingDelayTextView)
        bulkSmsSettingDelaySmsErrorTextView = view.findViewById(R.id.bulkSmsSettingDelaySmsErrorTextView)
        bulkSmsSettingDelaySmsIncrementTextView = view.findViewById(R.id.bulkSmsSettingDelaySmsIncrementTextView)
        bulkSmsSettingDeleteAllSmsButton = view.findViewById(R.id.bulkSmsSettingDeleteAllSmsButton)
        bulkSmsSettingStoreHistoryCheckBox = view.findViewById(R.id.bulkSmsSettingStoreHistoryCheckBox)
        bulkSmsSettingShowAdButton = view.findViewById(R.id.bulkSmsSettingShowAdButton)
    }
}