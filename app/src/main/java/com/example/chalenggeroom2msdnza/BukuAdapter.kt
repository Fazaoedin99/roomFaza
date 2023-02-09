package com.example.chalenggeroom2msdnza


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chalenggeroom2msdnza.room.tbBuku
import kotlinx.android.synthetic.main.activity_buku_adapter.view.*

class BukuAdapter (private val buku: ArrayList<tbBuku>,private val listener: onAdapterListener): RecyclerView.Adapter<BukuAdapter.BukuViewHolder>(){
    class BukuViewHolder (val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
      return BukuViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.activity_buku_adapter,parent,false)
      )
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
      val buk = buku[position]
        holder.view.t_nama.text = buk.judul
        holder.view.t_nama.setOnClickListener {
            listener.onClick(buk)
        }
        holder.view.image_edit.setOnClickListener{
            listener.onUpdate(buk)
        }
        holder.view.image_delete.setOnClickListener{
            listener.onDelete(buk)
        }
    }

    override fun getItemCount() = buku.size
    fun setData(list: List<tbBuku>){
        buku.clear()
        buku.addAll(list)
        notifyDataSetChanged()

    }
    interface onAdapterListener{
        fun onClick(tbbuk: tbBuku)
        fun onUpdate(tbbuk: tbBuku)
        fun onDelete(tbbuk: tbBuku)
    }

    }