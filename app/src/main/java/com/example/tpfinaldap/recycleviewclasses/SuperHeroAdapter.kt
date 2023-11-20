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

class SuperHeroAdapter(
     superHerolist: ArrayList<SuperHero>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
     private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<SuperHeroAdapter.SuperHeroViewHolder>(){
    private var YUGIOHlist: ArrayList<SuperHero>

    init {
        this.YUGIOHlist =superHerolist
    }

    class SuperHeroViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val Yugioh= view.findViewById<TextView>(R.id.tvYugioh)
        val MonsterType= view.findViewById<TextView>(R.id.tvMonsterType)
        val photo = view.findViewById<ImageView>(R.id.ivYUGIOH)
        val editar = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)

        fun render(superHeroModel: SuperHero){
            Yugioh.text = superHeroModel.yugioh
            MonsterType.text = superHeroModel.monstertype

            Glide.with(photo.context).load(superHeroModel.photo).into(photo)
            /*photo.setOnClickListener{
                Toast.makeText(photo.context, superHeroModel.realName, Toast.LENGTH_SHORT).show()
            }*/
            //itemView.setOnClickListener{ Toast.makeText(photo.context, superHeroModel.superhero, Toast.LENGTH_SHORT).show()}

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperHeroViewHolder(layoutInflater.inflate(R.layout.item_yugioh, parent, false))
    }
    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
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