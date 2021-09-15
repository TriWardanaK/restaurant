package com.example.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.adapter.FilterMenuAdapter
import com.example.restaurant.api.RetrofitClient
import com.example.restaurant.response.FilterDataMenuResponse
import kotlinx.android.synthetic.main.activity_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity() {

    companion object{
        const val NAMA_TYPE = "nama_type"
        const val HARGA_MIN = "harga_min"
        const val HARGA_MAX = "harga_max"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        showFilterMenu()
    }

    fun dataEmpty(){
        ivEmpty.visibility = View.VISIBLE
    }

    fun showLoading() {
        pb_filter.visibility = View.VISIBLE
    }

    fun hiddenLoading(){
        pb_filter.visibility = View.GONE
    }

    private fun getNamatype(): String?{
        return intent.getStringExtra(NAMA_TYPE)
    }

    private fun getHargaMin(): String?{
        return intent.getStringExtra(HARGA_MIN)
    }

    private fun getHargaMax(): String? {
        return intent.getStringExtra(HARGA_MAX)
    }

    private fun showFilterMenu() {
        rv_filter_menu.layoutManager = LinearLayoutManager(this)

            RetrofitClient.instance.getFilterMenu(getNamatype(), getHargaMin(), getHargaMax()).enqueue(object :
                Callback<FilterDataMenuResponse> {
                override fun onFailure(call: Call<FilterDataMenuResponse>, t: Throwable) {
                    Toast.makeText(this@FilterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<FilterDataMenuResponse>,
                    response: Response<FilterDataMenuResponse>
                ) {
                    showLoading()
                    val list = response.body()?.data
                    val filterMenuAdapter = list?.let { FilterMenuAdapter(it) }
                    rv_filter_menu.adapter = filterMenuAdapter
                    if (list?.size == 0) {
                        dataEmpty()
                    }
                    hiddenLoading()

                    filterMenuAdapter?.setOnItemClickCallback(object :
                        FilterMenuAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: FilterDataMenuResponse.FilterMenuResponse) {
    //                            showSearchSelectedPopular(data)
                        }
                    })
                }
            })
        }
//    private fun showSearchSelectedPopular(searchMobilResponse: SearchDataMobilResponse.SearchMobilResponse) {
//        val moveObject = Intent(this@SearchActivity, DetailActivity::class.java)
//        moveObject.putExtra(DetailActivity.EXTRA_SEARCH_ID, searchPopularResponse)
//        startActivity(moveObject)
//    }
}