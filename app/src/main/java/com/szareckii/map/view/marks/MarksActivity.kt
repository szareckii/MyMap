package com.szareckii.map.view.marks

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.szareckii.map.R
import com.szareckii.map.model.data.AppState
import com.szareckii.map.model.data.DataModel
import com.szareckii.map.view.base.BaseActivity
import com.szareckii.map.view.marks.adapter.MarksAdapter
import kotlinx.android.synthetic.main.activity_favorites.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val TAG = "dlg1"
private const val INDEX = "com.szareckii.map.view.marks.index"
private const val NAME = "com.szareckii.map.view.marks.name"
private const val DESCRIPTION = "com.szareckii.map.view.marks.description"

class MarksActivity : BaseActivity<AppState>(), MarkEditable {

    override val model: MarksViewModel by viewModel()

    private val adapter: MarksAdapter by lazy { MarksAdapter(onListItemClickListener) }

    private val onListItemClickListener: MarksAdapter.OnListItemClickListener =
        object : MarksAdapter.OnListItemClickListener {
            override fun onItemClick(listItemData: DataModel) {
                Toast.makeText(this@MarksActivity, listItemData.description, Toast.LENGTH_SHORT).show()

                val editMarkDialog = EditMarkDialogFragment()
                val args = Bundle()
                args.putInt(INDEX, listItemData.id)
                args.putString(NAME, listItemData.name)
                args.putString(DESCRIPTION, listItemData.description)
                editMarkDialog.arguments = args
                editMarkDialog.show(supportFragmentManager, TAG)
            }

            override fun onDeleteItemClick(listItemData: DataModel) {
//                deleteData(listItemData.id)
                deleteData(listItemData)
            }
        }

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
        if (marks_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@MarksActivity, { renderData(it) })
    }

    // Инициализируем адаптер и передаем его в RecyclerView
    private fun initViews() {
        marks_activity_recyclerview.adapter = adapter
    }

    override fun edit(index: Int, name: String, description: String) {
        model.editData(index, name, description)
    }

    fun deleteData(data : DataModel) {
        model.deleteData(data)
    }

}

