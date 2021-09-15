package com.example.restaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.restaurant.api.RetrofitClient
import com.example.restaurant.fragments.FavoriteFragment
import com.example.restaurant.fragments.HistoryFragment
import com.example.restaurant.fragments.HomeFragment
import com.example.restaurant.response.TypeDataMenuResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_filter.*
import kotlinx.android.synthetic.main.item_filter.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val favoriteFragment = FavoriteFragment()
    private val historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(homeFragment)
        replaceTitle(getString(R.string.home))

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {
                    replaceTitle(getString(R.string.home))
                    replaceFragment(homeFragment)
                }
                R.id.ic_favorite -> {
                    replaceTitle(getString(R.string.favorite))
                    replaceFragment(favoriteFragment)

                }
                R.id.ic_history -> {
                    replaceTitle(getString(R.string.history))
                    replaceFragment(historyFragment)
                }
            }
            true
        }
    }

    private fun replaceTitle(title: String){
        supportActionBar?.title = title
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)

        val searchView: SearchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val moveObject = Intent(this@MainActivity, SearchActivity::class.java)
                moveObject.putExtra(SearchActivity.EXTRA_NAME, query)
                startActivity(moveObject)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {

                val mDialogView = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_filter, null)

                val mBuilder = AlertDialog.Builder(this@MainActivity)
                        .setView(mDialogView)

                val mAlertDialog = mBuilder.show()

                mDialogView.btn_set_submit.setOnClickListener {

                    val namaType = mDialogView.filter_spinner.selectedItem.toString()
                    val hargaMin = mDialogView.edt_harga_min.text.toString()
                    val hargaMax = mDialogView.edt_harga_max.text.toString()

                    when {
                        hargaMin.isEmpty() -> {
                            mDialogView.edt_harga_min.error = "Masih kosong"
                        }
                        hargaMax.isEmpty() -> {
                            mDialogView.edt_harga_max.error = "Masih kosong"
                        }
                        else -> {
                            val filter = Intent(this@MainActivity, FilterActivity::class.java)
                            filter.putExtra(FilterActivity.NAMA_TYPE, namaType)
                            filter.putExtra(FilterActivity.HARGA_MIN, hargaMin)
                            filter.putExtra(FilterActivity.HARGA_MAX, hargaMax)
                            startActivity(filter)
                            mAlertDialog.dismiss()
                        }
                    }

                }
                mDialogView.btn_set_cancel.setOnClickListener {
                    mAlertDialog.dismiss()
                }

                RetrofitClient.instance.getTypeMenu().enqueue(object :
                        Callback<TypeDataMenuResponse> {
                    override fun onFailure(call: Call<TypeDataMenuResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                            call: Call<TypeDataMenuResponse>,
                            response: Response<TypeDataMenuResponse>
                    ) {
                        val list = response.body()?.data

                        var start = 0
                        val list2 = ArrayList<String?>()
                        val size = list?.size
                        while (start < size!!) {
                            list2.add(list[start].namaType)
                            start++
                        }

                        if (mDialogView.filter_spinner != null) {
                            val arrayAdapter =
                                    ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, list2)
                            mDialogView.filter_spinner.adapter = arrayAdapter
                        }
                    }
                })
//                val adminId = intent.getParcelableExtra(EXTRA_ADMIN_ID) as DataLoginResponse?
//
//                val intent = Intent(this, PengembalianBookActivity::class.java)
//                intent.putExtra(PengembalianBookActivity.EXTRA_ADMIN_ID, adminId)
//                startActivity(intent)
                true
            }
            else -> true
        }
    }
}