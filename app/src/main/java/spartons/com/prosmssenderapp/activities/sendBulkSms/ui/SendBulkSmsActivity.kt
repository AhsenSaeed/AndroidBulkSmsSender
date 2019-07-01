package spartons.com.prosmssenderapp.activities.sendBulkSms.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_send_bulk_sms.*
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.BaseActivity
import spartons.com.prosmssenderapp.activities.sendBulkSms.adapter.SendBulkSmsContactAdapter
import spartons.com.prosmssenderapp.activities.sendBulkSms.viewModel.SendBulkSmsViewModel
import spartons.com.prosmssenderapp.helper.UiHelper
import spartons.com.prosmssenderapp.util.askPermission
import spartons.com.prosmssenderapp.util.doOnTextChanged
import spartons.com.prosmssenderapp.util.getResourceString
import spartons.com.prosmssenderapp.util.isHasPermission
import javax.inject.Inject


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class SendBulkSmsActivity : BaseActivity() {

    private companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1000
        private const val ASK_SMS_PERMISSION_REQUEST_CODE = 1001
    }

    @Inject
    lateinit var uiHelper: UiHelper

    private val smsContactAdapter by lazy {
        SendBulkSmsContactAdapter(context = this)
    }

    private lateinit var viewModel: SendBulkSmsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_bulk_sms)
        activityComponent.inject(this)
        viewModel = ViewModelProviders.of(this)[SendBulkSmsViewModel::class.java]
        sendBulkSmsToolbar.title = getResourceString(R.string.send_bulk_sms)
        setSupportActionBar(sendBulkSmsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /**
         *
         */

        sendBulkSmsChooseFileButton.setOnClickListener {
            if (isHasPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                showFileSelectorDialog()
            else
                askPermission(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                )
        }

        /**
         * Initializing the Contact list recycler view and setting default adapter.
         */

        sendBulkSmsAllPhoneNumberRecyclerView.layoutManager = LinearLayoutManager(this)
        sendBulkSmsAllPhoneNumberRecyclerView.setHasFixedSize(true)
        sendBulkSmsAllPhoneNumberRecyclerView.adapter = smsContactAdapter

        /**
         * handle events came from SendBulkSmsViewModel class.
         */

        viewModel.uiState.observe(this, Observer {
            val uiModel = it ?: return@Observer
            if (uiModel.showProgress)
                sendBulkSmsProgressBar.visibility = VISIBLE
            if (uiModel.contactList != null && !uiModel.contactList.consumed)
                uiModel.contactList.consume()?.let { contactList ->
                    sendBulkSmsChooseFileButton.text = getResourceString(R.string.add_another_text_file)
                    smsContactAdapter.feedItems(contactList)
                }
            if (uiModel.showMessage != null && !uiModel.showMessage.consumed)
                uiModel.showMessage.consume()?.let { messageResource ->
                    uiHelper.showSnackBar(
                        sendBulkSmsActivityRootView,
                        getResourceString(messageResource), Snackbar.LENGTH_LONG
                    )
                }
            if (uiModel.hideProgressBar && sendBulkSmsProgressBar.isVisible)
                sendBulkSmsProgressBar.visibility = View.GONE
        })

        /**
         * handles the click listener for send bulk sms image view.
         */

        sendBulkSmsImageView.setOnClickListener {
            if (smsContactAdapter.isContactListEmpty()) {
                uiHelper.showSnackBar(
                    sendBulkSmsActivityRootView,
                    getResourceString(R.string.please_first_select_contact_list), Snackbar.LENGTH_LONG
                )
                return@setOnClickListener
            }
            if (sendBulkSmsTextMessageEditText.text.toString().isEmpty()) {
                sendBulkSmsTextMessageLayout.helperText = getResourceString(R.string.sms_content_cannot_be_empty)
                return@setOnClickListener
            }
            if (isHasPermission(Manifest.permission.SEND_SMS))
                sendBulkSms()
            else
                askPermission(arrayOf(Manifest.permission.SEND_SMS), ASK_SMS_PERMISSION_REQUEST_CODE)
        }

        /**
         * listening for data added to bulk sms edit text and hide the helper text if it is showing.
         */

        sendBulkSmsTextMessageEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.isNotEmpty())
                sendBulkSmsTextMessageLayout.isHelperTextEnabled = false
        }
    }

    private fun sendBulkSms() {
        viewModel.sendBulkSms(
            smsContactAdapter.contactItems().toTypedArray(),
            sendBulkSmsTextMessageEditText.text.toString()
        )
        uiHelper.showSimpleMaterialDialog(
            this,
            R.string.bulk_sms_sent,
            R.string.sms_sent_successfully_content,
            icon = R.drawable.tick_image_icon
        ) {
            finish()
        }
    }

    private fun showFileSelectorDialog() {
        uiHelper.showSelectContactFileDialog(this) { selectedFile ->
            viewModel.handleSelectedFile(selectedFile)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ASK_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                sendBulkSms()
            else
                showBottomSheetDialog(R.string.sms_permission_denied_content) {
                    askPermission(arrayOf(Manifest.permission.SEND_SMS), ASK_SMS_PERMISSION_REQUEST_CODE)
                }
        } else if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                showFileSelectorDialog()
            else
                showBottomSheetDialog(R.string.storage_permission_denied_content) {
                    askPermission(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                    )
                }
        }
    }

    private fun showBottomSheetDialog(@StringRes titleResource: Int, closure: () -> Unit) {
        uiHelper.showBottomSheetDialog(
            this,
            titleResource,
            R.string.storage_permission_denied,
            positiveButtonText = "Ask Permission"
        ) {
            closure.invoke()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}