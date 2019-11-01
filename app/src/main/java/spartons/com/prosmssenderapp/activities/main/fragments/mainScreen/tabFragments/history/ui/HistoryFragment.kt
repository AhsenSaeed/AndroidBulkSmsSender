package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.adapter.BulkSmsAdapter
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewModel.HistoryFragmentViewModel
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.helper.UiHelper
import spartons.com.prosmssenderapp.roomPersistence.BulkSms

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class HistoryFragment : BaseFragment(), BulkSmsAdapter.IBulkSmsDeleteListener {

    private val bulkSmsAdapter = BulkSmsAdapter(this)
    private val viewModel: HistoryFragmentViewModel by viewModel()
    private val uiHelper: UiHelper by inject()

    companion object {
        fun getInstance() = HistoryFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_history

    override fun inOnCreateView(
        mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?
    ) {
        val bulkSmsHistoryNotFoundView =
            mRootView.findViewById<LinearLayout>(R.id.bulkSmsHistoryNotFoundView)

        mRootView.findViewById<RecyclerView>(R.id.bulkSmsHistoryRecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = bulkSmsAdapter
        }

        viewModel.allTasks.observe(this, Observer {
            bulkSmsAdapter.submitList(it)
            bulkSmsHistoryNotFoundView.visibility = if (it.isEmpty()) VISIBLE else GONE
        })
    }

    override fun onBulkSmsAction(clickType: BulkSmsAdapter.ClickType, bulkSms: BulkSms) {
        if (clickType == BulkSmsAdapter.ClickType.DELETE)
            viewModel.deleteBulkSms(bulkSms)
        else if (clickType == BulkSmsAdapter.ClickType.CANCEL) {
            uiHelper.showSimpleMaterialDialog(
                requireActivity(),
                R.string.sms_send,
                R.string.are_you_sure_you_want_to_cancel_the_on_going_operation,
                R.drawable.red_error_vector_icon, "Yes", true, negativeText = "Cancel"
            ) {
                viewModel.cancelBulkSmsOperation(bulkSms)
            }
        }
    }
}
