package com.example.restaurant.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.R
import com.example.restaurant.adapter.MenuAdapter
import com.example.restaurant.api.RetrofitClient
import com.example.restaurant.response.DataMenuResponse
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBook()

        refreshLayout.setOnRefreshListener {
            showBook()
            refreshLayout.isRefreshing = false
        }
    }

    private fun showBook() {
        rv_menu.setHasFixedSize(true)
        rv_menu.layoutManager = LinearLayoutManager(context)

        RetrofitClient.instance.getMenu().enqueue(object :
            Callback<DataMenuResponse> {
            override fun onFailure(call: Call<DataMenuResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataMenuResponse>,
                response: Response<DataMenuResponse>
            ) {
                val list = response.body()?.data
                val menuAdapter = list?.let { MenuAdapter(it) }
                rv_menu.adapter = menuAdapter

                menuAdapter?.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataMenuResponse.MenuResponse) {
//                        showSelectedMenu(data)
                    }
                })
            }
        })
    }

//    private fun showSelectedMenu(bukuResponse: DataMenuResponse.MenuResponse){
//        val memberId = intent.getParcelableExtra(EXTRA_MEMBER_ID) as DataLoginResponse?
//
//        val moveObject = Intent(this@MainActivity, DetailActivity::class.java)
//        moveObject.putExtra(DetailActivity.EXTRA_BOOK_ID, bukuResponse)
//        moveObject.putExtra(DetailActivity.EXTRA_MEMBER_ID, memberId)
//        startActivity(moveObject)
//    }


    //    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_action, menu)
//
//        val searchView: SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                val moveObject = Intent(this@MainActivity, SearchActivity::class.java)
//                moveObject.putExtra(SearchActivity.EXTRA_NAME, query)
//                startActivity(moveObject)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//        return true
//    }
}