package com.example.chalenggeroom2msdnza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chalenggeroom2msdnza.room.Constant
import com.example.chalenggeroom2msdnza.room.dbPerpustakaan
import com.example.chalenggeroom2msdnza.room.tbBuku
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var BukuAdapter: BukuAdapter
    val db by lazy { dbPerpustakaan (this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setuRecyclerView()
    }

    override fun onStart(){
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.tbBookDAO().tampilsemua()
            Log.d("MainActivity", "dbResponse:$buku")
            withContext(Dispatchers.Main) {
                BukuAdapter.setData(buku)
            }
        }
    }

    private fun halEdit() {
        btninput.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbbukuid: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", tbbukuid)
                .putExtra("intent_type", intentType)
        )
    }

    fun setuRecyclerView(){
        BukuAdapter = BukuAdapter(arrayListOf(), object : BukuAdapter.onAdapterListener {
            override fun onClick(tbbuk: tbBuku) {
                intentEdit(tbbuk.id_buku, Constant.TYPE_READ)

            }
            override fun onUpdate(tbbuk: tbBuku) {
                intentEdit(tbbuk.id_buku, Constant.TYPE_UPDATE)
            }
            override fun onDelete(tbbuk: tbBuku) {
                deleteAlert(tbbuk)
            }
        })
        //idRecyclerView
        listdatabuku.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BukuAdapter
        }
    }
    private fun deleteAlert(tbBuk: tbBuku) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${tbBuk.judul}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBookDAO().delttbbuku(tbBuk)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }
}