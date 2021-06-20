package com.szareckii.map.view.favorites

import android.os.Bundle
import android.view.MenuItem
import com.szareckii.map.R
import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.view.base.BaseActivity
import com.szareckii.map.view.favorites.adapter.HistoryAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_favorites.*


class FavoritesActivity : BaseActivity<AppState>() {

    override val model: FavoritesViewModel by viewModel()

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        setActionbarHomeButtonAsUp()
        iniViewModel()
        initViews()
    }

    // Сразу запрашиваем данные из локального репозитория
    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    // Вызовется из базовой Activity, когда данные будут готовы
    fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    // Переопределяем нажатие на стрелку Назад, чтобы возвращаться по нему
    // на главный экран
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Устанавливаем кнопку Назад в ActionBar
    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun iniViewModel() {
        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@FavoritesActivity, Observer<AppState> { renderData(it) })
    }

    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }

}

