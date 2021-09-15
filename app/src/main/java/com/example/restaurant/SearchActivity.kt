package com.example.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.adapter.SearchMenuAdapter
import com.example.restaurant.api.RetrofitClient
import com.example.restaurant.response.SearchDataMenuResponse
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        showSearchMenu()
    }

    fun dataEmpty(){
        ivEmpty.visibility = View.VISIBLE
    }

    fun showLoading() {
        pb_search.visibility = View.VISIBLE
    }

    fun hiddenLoading(){
        pb_search.visibility = View.GONE
    }


    private fun getName(): String? {
        return intent.getStringExtra(EXTRA_NAME)
    }

    private fun showSearchMenu() {
        rv_search_menu.layoutManager = LinearLayoutManager(this)

        getName()?.let {
            RetrofitClient.instance.getSearchMenu(it).enqueue(object :
                Callback<SearchDataMenuResponse> {
                override fun onFailure(call: Call<SearchDataMenuResponse>, t: Throwable) {
                    Toast.makeText(this@SearchActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<SearchDataMenuResponse>,
                    response: Response<SearchDataMenuResponse>
                ) {
                    showLoading()
                    val list = response.body()?.data
                    val searchMenuAdapter = list?.let { SearchMenuAdapter(it) }
                    rv_search_menu.adapter = searchMenuAdapter
                    if (list?.size == 0) {
                        dataEmpty()
                    }
                    hiddenLoading()

                    searchMenuAdapter?.setOnItemClickCallback(object :
                        SearchMenuAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: SearchDataMenuResponse.SearchMenuResponse) {
//                            showSearchSelectedPopular(data)
                        }
                    })
                }
            })
        }
    }

//    private fun showSearchSelectedPopular(searchMobilResponse: SearchDataMobilResponse.SearchMobilResponse) {
//        val moveObject = Intent(this@SearchActivity, DetailActivity::class.java)
//        moveObject.putExtra(DetailActivity.EXTRA_SEARCH_ID, searchPopularResponse)
//        startActivity(moveObject)
//    }
}