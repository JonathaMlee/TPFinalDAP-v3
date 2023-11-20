package com.example.tpfinaldap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpfinaldap.recycleviewclasses.SuperHero
import com.example.tpfinaldap.recycleviewclasses.SuperHeroAdapter
import com.example.tpfinaldap.viewmodels.PantallaInicioViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PantallaInicio : Fragment() {

    private lateinit var viewModel: PantallaInicioViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var superHeroList: ArrayList<SuperHero>
    private var db = Firebase.firestore
    private lateinit var botonAdd: Button
    private lateinit var idSuperHeroeActual: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : SuperHeroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerYUGIOH)
        recyclerView.layoutManager = LinearLayoutManager(context)
        superHeroList = arrayListOf()
        botonAdd = view.findViewById(R.id.botonAñadir)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicio_to_subirYUGIOH)
        }
        return view
    }

    private fun initRecyclerView() {
        db.collection("SuperHeroes").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val superHero:SuperHero? = data.toObject<SuperHero>(SuperHero::class.java)
                    superHeroList.add(superHero!!)
                }

                adapter = SuperHeroAdapter(superHeroList,
                    onDeleteClick = {position -> deleteHero(position)
                },
                    onEditClick = {position -> editSuperHero(position)
                },
                    onItemClick = {position -> seeSuperHeroData(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeSuperHeroData(position:Int) {

        idSuperHeroeActual = superHeroList[position].idSuperHero.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idSuperHeroeActual

        findNavController().navigate(R.id.action_pantallaInicio_to_dataSuperHeroes)
    }

    fun editSuperHero(position: Int) {
        idSuperHeroeActual = superHeroList[position].idSuperHero.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idSuperHeroeActual

        findNavController().navigate(R.id.action_pantallaInicio_to_editYUGIOH)
    }

    fun deleteHero (position : Int){

        db.collection("SuperHeroes").document(superHeroList[position].idSuperHero.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Super Heroe Eliminado", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                superHeroList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"No se puedo eliminar el Super Heroe", Toast.LENGTH_SHORT).show() }
    }
}