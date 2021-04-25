package com.recycyclersample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.recycyclersample.callbacks.OnItemClickListener
import com.recycyclersample.models.Response
import com.recycyclersample.models.ResultsItem
import com.recycyclersample.utils.BaseView
import com.recycyclersample.utils.GeneralAdapter
import com.recycyclersample.retrofit.RetroService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    lateinit var generalAdapter: GeneralAdapter

    override fun initView() {
        val call = App.instance?.apiServices?.getMusicData("Michael+jackson")
        RetroService().call(this, call, object : RetroService.SuccessListener<Response?> {
            override fun onSuccess(result: Response?) {
                showMusicList(result?.results)
            }
        })
    }

    private fun showMusicList(results: ArrayList<ResultsItem?>?) {
        generalAdapter = GeneralAdapter(R.layout.row_music, results, object : OnItemClickListener<Any?> {
            override fun onItemClick(view: View?, `object`: Any?) {

            }
        })
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = generalAdapter
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun exception(e: Exception?) {
        App.instance?.toast(e?.message)
    }
}