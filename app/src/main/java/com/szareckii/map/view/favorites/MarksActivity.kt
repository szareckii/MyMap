package com.szareckii.map.view.favorites

import android.os.Bundle
import android.view.MenuItem
import com.szareckii.map.R
import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.view.base.BaseActivity
import com.szareckii.map.view.favorites.adapter.MarksAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_favorites.*


class MarksActivity : BaseActivity<AppState>() {

    override val model: MarksViewModel by viewModel()

    private val adapter: MarksAdapter by lazy { MarksAdapter() }

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
        model.getData()
    }

    // Вызовется из базовой Activity, когда данные будут готовы
    override fun setDataToAdapter(data: MutableList<DataModel>) {
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
        model.subscribe().observe(this@MarksActivity, { renderData(it) })
    }

    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }

}

