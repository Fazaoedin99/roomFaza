package com.example.chalenggeroom2msdnza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chalenggeroom2msdnza.room.Constant
import com.example.chalenggeroom2msdnza.room.dbPerpustakaan
import com.example.chalenggeroom2msdnza.room.tbBuku
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity: AppCompatActivity() {

    private val db by lazy { dbPerpustakaan(this) }
    private var tbbukuid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        tombolPerintah()
        setupView()
        tbbukuid = intent.getIntExtra("intent_id", tbbukuid)
        Toast.makeText(this, tbbukuid.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ-> {
                btnSimpan.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                etId.visibility = View.GONE
                tampildatanis()
            }
            Constant.TYPE_UPDATE->{
                btnSimpan.visibility = View.GONE
                etId.visibility = View.GONE
                tampildatanis()
            }

        }
    }

    //2
    fun tombolPerintah() {
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBookDAO().addtbbuku(
                    tbBuku(
                        etId.text.toString().toInt(),
                        etJumlah.text.toString().toInt(),
                        etKategori.text.toString(),
                        etJudul.text.toString(),
                        etPengarang.text.toString(),
                        etPenerbit.text.toString()
                    )
                )
                finish()
            }
        }
        //menambah perintah tombol update3
        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.tbBookDAO().updatetbbuku(
                    tbBuku( etId.text.toString().toInt(),
                        etJumlah.text.toString().toInt(),
                        etKategori.text.toString(),
                        etJudul.text.toString(),
                        etPengarang.text.toString(),
                        etPenerbit.text.toString())
                )
                finish()
            }
        }
    }

    fun tampildatanis(){
        tbbukuid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            val notes = db.tbBookDAO().tampilid(tbbukuid).get(0)
            val idbook : String = notes.id_buku.toString()
            etId.setText(idbook)
            etJudul.setText(notes.judul)
            //etKategori.setText(notes.kategori)
            etPenerbit.setText(notes.penerbit)
            etPengarang.setText(notes.pengarang)
            etJumlah.setText(notes.jml_buku)

        }
    }

}