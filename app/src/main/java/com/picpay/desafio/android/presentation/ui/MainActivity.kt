package com.picpay.desafio.android.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.repogit.utils.subscribe
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewmodel.PicPayViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: PicPayViewModel by viewModel()
    private val viewBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        UserListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        subscribeEvents()
        configureRecyclerView()
        viewModel.getUsers()
    }

    private fun subscribeEvents() {
        viewModel.loadingEvent.subscribe(this) {
            viewBinding.userListProgressBar.visibility = View.VISIBLE
        }

        viewModel.emptyResponseEvent.subscribe(this) {
            showFullResultsSnackbar()
            hideLoading()
        }

        viewModel.fullResultResponseEvent.subscribe(this) {
            showFullResultsSnackbar()
        }

        viewModel.errorResponseEvent.subscribe(this) {
            showGenericErrorSnackbar()
            hideLoading()
        }

        viewModel.successResponseEvent.subscribe(this) {
            adapter.update(this.data)
            hideLoading()
        }
    }

    private fun hideLoading() {
        viewBinding.userListProgressBar.visibility = View.GONE
    }

    private fun configureRecyclerView() {
        viewBinding.recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewBinding.recyclerView.layoutManager = layoutManager
    }

    private fun showFullResultsSnackbar() {
        var snackbar: Snackbar? = null
        snackbar = Snackbar.make(
            viewBinding.recyclerView,
            R.string.full_results,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.back_to_top) {
                viewBinding.recyclerView.scrollToPosition(0)
                snackbar?.dismiss()
            }
        snackbar.show()
    }

    private fun showGenericErrorSnackbar() {
        var snackbar: Snackbar? = null
        snackbar = Snackbar.make(
            viewBinding.recyclerView,
            R.string.couldnt_load,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.try_again) {
                viewModel.getUsers()
                snackbar?.dismiss()
            }
        snackbar.show()
    }
}
