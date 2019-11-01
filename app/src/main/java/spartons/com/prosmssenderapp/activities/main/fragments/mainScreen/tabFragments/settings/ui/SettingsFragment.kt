package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.settings.viewModel.SettingsFragmentViewModel
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_MESSAGE_DELAY_SECONDS
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper.Companion.BULK_SMS_PREFERRED_MULTIPLE_CARRIER_NUMBER_FLAG
import spartons.com.prosmssenderapp.helper.UiHelper
import spartons.com.prosmssenderapp.util.*
import spartons.com.prosmssenderapp.util.Constants.CARRIER_NAME_SPLITTER

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class SettingsFragment : BaseFragment() {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()
    private val viewModel: SettingsFragmentViewModel by viewModel()
    private val uiHelper: UiHelper by inject()

    companion object {
        private const val ASK_READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 1002
        private const val LEAST_MINIMUM_SMS_DELAY_VALUE = 4

        fun getInstance() = SettingsFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_settings

    override fun inOnCreateView(
        mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?
    ) {

        val bulkSmsSettingNoteTextView =
            mRootView.findViewById<TextView>(R.id.bulkSmsSettingNoteTextView)
        val bulkSmsSettingDelayTextView =
            mRootView.findViewById<TextView>(R.id.bulkSmsSettingDelayTextView)
        val bulkSmsSettingDelaySmsErrorTextView =
            mRootView.findViewById<TextView>(R.id.bulkSmsSettingDelaySmsErrorTextView)
        val bulkSmsSettingDefaultCarrierNumberTextView =
            mRootView.findViewById<TextView>(R.id.bulkSmsSettingDefaultCarrierNumberTextView)
        val bulkSmsSettingChangeCarrierButton =
            mRootView.findViewById<MaterialButton>(R.id.bulkSmsSettingChangeCarrierButton)

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

        mRootView.findViewById<TextView>(R.id.bulkSmsSettingDelaySmsDecrementTextView)
            .setOnClickListener {
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
                sharedPreferenceHelper.put(BULK_SMS_MESSAGE_DELAY_SECONDS, delayValue)
            }

        /**
         * Increment the delay sms in seconds.
         */

        mRootView.findViewById<TextView>(R.id.bulkSmsSettingDelaySmsIncrementTextView)
            .setOnClickListener {
                var delayValue = bulkSmsSettingDelayTextView.text.toString().toInt()
                bulkSmsSettingDelayTextView.text = (++delayValue).toString()
                if (bulkSmsSettingDelaySmsErrorTextView.isVisible)
                    bulkSmsSettingDelaySmsErrorTextView.visibility = GONE
                sharedPreferenceHelper.put(BULK_SMS_MESSAGE_DELAY_SECONDS, delayValue)
            }

        /**
         * Delete all bulk sms history from SQLite database.
         */

        mRootView.findViewById<MaterialButton>(R.id.bulkSmsSettingDeleteAllSmsButton)
            .setOnClickListener {
                viewModel.deleteAllBulkSms()
                requireActivity().snackbar(
                    mRootView, getResourceString(R.string.all_sms_history_deleted_successfully)
                )
            }

        /**
         * Retrieving the current delay sms from shared preference and setting it.
         */

        val delayValue = sharedPreferenceHelper.getInt(BULK_SMS_MESSAGE_DELAY_SECONDS)
        bulkSmsSettingDelayTextView.text = delayValue.toString()

        mRootView.findViewById<MaterialButton>(R.id.bulkSmsSettingShowAdButton).setOnClickListener {
            requireActivity().snackbar(mRootView, getResourceString(R.string.feature_not_added_yet))
        }

        val preferredCarrierNumber =
            sharedPreferenceHelper.getString(SharedPreferenceHelper.BULK_SMS_PREFERRED_CARRIER_NUMBER)
                ?.split(CARRIER_NAME_SPLITTER)?.get(1)

        if (preferredCarrierNumber != null) {
            bulkSmsSettingDefaultCarrierNumberTextView.visibility = VISIBLE
            updateCurrentCarrierText(
                bulkSmsSettingDefaultCarrierNumberTextView,
                preferredCarrierNumber
            )
        }

        if (sharedPreferenceHelper.getBoolean(BULK_SMS_PREFERRED_MULTIPLE_CARRIER_NUMBER_FLAG))
            bulkSmsSettingChangeCarrierButton.visibility = VISIBLE

        bulkSmsSettingChangeCarrierButton.setOnClickListener {
            if (requireActivity().isHasPermission(Manifest.permission.READ_PHONE_STATE))
                viewModel.handleDeviceCarrierNumbers()
            else
                uiHelper.showSimpleMaterialDialog(
                    requireActivity(),
                    R.string.allow_following_permission,
                    R.string.read_phone_state_permission_content, null,
                    "Grant Permission", false, negativeText = "Cancel",
                    negativeTextButtonListener = { requireContext().toast("Cancelled the operation") }) {
                    requireActivity().askPermission(
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        ASK_READ_PHONE_STATE_PERMISSION_REQUEST_CODE
                    )
                }
        }

        viewModel.uiState.observe(this, Observer {
            val uiModel = it ?: return@Observer
            if (uiModel.noDeviceNumber)
                requireContext().toast(
                    "Seems like you ejected the carrier network sims, please insert then try again",
                    Toast.LENGTH_LONG
                )
            if (uiModel.showMultipleCarrierNumber != null && !uiModel.showMultipleCarrierNumber.consumed)
                uiModel.showMultipleCarrierNumber.consume()?.let { carriers ->
                    uiHelper.showSingleSelectionListDialog(
                        requireActivity(),
                        R.string.please_select_from_which_carrier_number_you_want_to_send_bulk_sms,
                        cancelable = true,
                        choices = carriers
                    ) { (number, checkedValue) ->
                        sharedPreferenceHelper.put(
                            SharedPreferenceHelper.BULK_SMS_PREFERRED_CARRIER_NUMBER,
                            number
                        )
                        sharedPreferenceHelper.put(
                            BULK_SMS_PREFERRED_MULTIPLE_CARRIER_NUMBER_FLAG, checkedValue
                        )
                        updateCurrentCarrierText(
                            bulkSmsSettingDefaultCarrierNumberTextView,
                            number.split(CARRIER_NAME_SPLITTER)[1]
                        )
                    }
                }
        })
    }

    private fun updateCurrentCarrierText(textView: TextView, content: String) {
        textView.text =
            String.format(
                getString(
                    R.string.setting_default_carrier_content, content
                )
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ASK_READ_PHONE_STATE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                viewModel.handleDeviceCarrierNumbers()
            else
                uiHelper.showBottomSheetDialog(
                    requireActivity(), R.string.phone_state_permission_denied_content,
                    R.string.phone_state_permission_denied,
                    "Ask Permission"
                ) {
                    requireActivity().askPermission(
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        ASK_READ_PHONE_STATE_PERMISSION_REQUEST_CODE
                    )
                }
        }
    }
}
