package com.example.expressionevaluator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expressionevaluator.databinding.ActivityMainBinding
import com.example.expressionevaluator.db.ExpressionDatabase
import com.example.expressionevaluator.viewmodels.HistoryViewModel
import com.example.expressionevaluator.viewmodels.HistoryViewModelFactory
import com.example.expressionevaluator.viewmodels.MainViewModel
import com.example.expressionevaluator.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val expressionDao = ExpressionDatabase.getDatabase(application).expressionDao()
        historyViewModel = ViewModelProvider(this, HistoryViewModelFactory(expressionDao)).get(HistoryViewModel::class.java)
        val recyclerView = binding.recyclerViewExpressionResultHistory
        val adapter = RecyclerViewAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        historyViewModel.expressionsLiveData.observe(this) {
            adapter.expressionList = it
            adapter.notifyDataSetChanged()
        }

        val repository = (application as ExpressionApplication).expressionRepository
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.expressions.observe(this) {
            mainViewModel.showProgressBar(false)
            binding.textViewResult.text = "= ${it.result.toString()}"
            adapter.notifyDataSetChanged()
            Log.d("TEST_EXPRESSION_DATA", it.result.toString())
        }
    }
 }