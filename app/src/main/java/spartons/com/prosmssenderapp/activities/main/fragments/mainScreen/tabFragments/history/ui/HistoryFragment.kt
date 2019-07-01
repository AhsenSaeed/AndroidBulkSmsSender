package spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import spartons.com.prosmssenderapp.R
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.adapter.AllBulkSmsRecyclerViewAdapter
import spartons.com.prosmssenderapp.activities.main.fragments.mainScreen.tabFragments.history.viewModel.HistoryFragmentViewModel
import spartons.com.prosmssenderapp.fragments.BaseFragment
import spartons.com.prosmssenderapp.roomPersistence.BulkSms
import spartons.com.prosmssenderapp.util.ViewModelFactory
import javax.inject.Inject


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */

class HistoryFragment : BaseFragment(), AllBulkSmsRecyclerViewAdapter.IBulkSmsDeleteListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: HistoryFragmentViewModel

    private val allBulkSmsAdapter by lazy {
        AllBulkSmsRecyclerViewAdapter(arrayListOf(), requireContext(), this)
    }

    companion object {
        fun getInstance() =
            HistoryFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_history

    override fun inOnCreateView(mRootView: View, container: ViewGroup?, savedInstanceState: Bundle?) {
        fragmentComponent.inject(this)
        val bulkSmsHistoryNotFoundView = mRootView.findViewById<LinearLayout>(R.id.bulkSmsHistoryNotFoundView)
        val bulkSmsHistoryRecyclerView = mRootView.findViewById<RecyclerView>(R.id.bulkSmsHistoryRecyclerView)

        bulkSmsHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bulkSmsHistoryRecyclerView.setHasFixedSize(true)
        bulkSmsHistoryRecyclerView.adapter = allBulkSmsAdapter

        viewModel = ViewModelProviders.of(this, factory)[HistoryFragmentViewModel::class.java]
        viewModel.allTasks.observe(this, Observer {
            if (it.isEmpty())
                bulkSmsHistoryNotFoundView.visibility = VISIBLE
            else
                bulkSmsHistoryNotFoundView.visibility = GONE
            allBulkSmsAdapter.updateBulkSmsItems(it)
        })
    }

    override fun onBulkSmsDelete(bulkSms: BulkSms) {
        viewModel.deleteBulkSms(bulkSms)
    }
}
