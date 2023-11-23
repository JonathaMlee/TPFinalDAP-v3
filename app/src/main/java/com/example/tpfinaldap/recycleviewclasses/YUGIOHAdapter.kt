package com.example.tpfinaldap.recycleviewclasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tpfinaldap.R

class YUGIOHAdapter(
    YUGIOHlist: ArrayList<Yugioh>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
    private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<YUGIOHAdapter.YUGIOHViewHolder>(){
    private var YUGIOHlist: ArrayList<Yugioh>

    init {
        this.YUGIOHlist =YUGIOHlist
    }

    class YUGIOHViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val yugioh= view.findViewById<TextView>(R.id.tvYugioh)
        val monstertype= view.findViewById<TextView>(R.id.tvMonsterType)
        val photo = view.findViewById<ImageView>(R.id.ivYUGIOH)
        val editar = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)

        fun render(YUGIOHModel: Yugioh){
            yugioh.text = YUGIOHModel.yugioh
            monstertype.text = YUGIOHModel.monstertype

            Glide.with(photo.context).load(YUGIOHModel.photo).into(photo)
            /*photo.setOnClickListener{
                Toast.makeText(photo.context, superHeroModel.realName, Toast.LENGTH_SHORT).show()
            }*/
            //itemView.setOnClickListener{ Toast.makeText(photo.context, superHeroModel.superhero, Toast.LENGTH_SHORT).show()}

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YUGIOHViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return YUGIOHViewHolder(layoutInflater.inflate(R.layout.item_yugioh, parent, false))
    }
    override fun onBindViewHolder(holder: YUGIOHViewHolder, position: Int) {
        val item = YUGIOHlist[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.photo.setOnClickListener {
            onItemClick(position)
        }
    }
    override fun getItemCount(): Int = YUGIOHlist.size

}