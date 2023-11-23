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
import com.example.tpfinaldap.recycleviewclasses.Yugioh
import com.example.tpfinaldap.recycleviewclasses.YUGIOHAdapter
import com.example.tpfinaldap.viewmodels.PantallaInicioViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PantallaInicio : Fragment() {

    private lateinit var viewModel: PantallaInicioViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var YUGIOHList: ArrayList<Yugioh>
    private var db = Firebase.firestore
    private lateinit var botonAdd: Button
    private lateinit var idYUGIOHActual: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : YUGIOHAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerYUGIOH)
        recyclerView.layoutManager = LinearLayoutManager(context)
        YUGIOHList = arrayListOf()
        botonAdd = view.findViewById(R.id.botonAñadir)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicio_to_subirYUGIOH)
        }
        return view
    }

    private fun initRecyclerView() {
        db.collection("YUGIOH").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val Yugioh:Yugioh? = data.toObject<Yugioh>(Yugioh::class.java)
                    YUGIOHList.add(Yugioh!!)
                }

                adapter = YUGIOHAdapter(YUGIOHList,
                    onDeleteClick = {position -> deleteyugioh(position)
                },
                    onEditClick = {position -> edityugioh(position)
                },
                    onItemClick = {position -> seeyugioh(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeyugioh(position:Int) {

        idYUGIOHActual = YUGIOHList[position].idYUGIOH.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idYUGIOHActual

        findNavController().navigate(R.id.action_pantallaInicio_to_dataYUGIOH)
    }

    fun edityugioh(position: Int) {
        idYUGIOHActual = YUGIOHList[position].idYUGIOH.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idYUGIOHActual

        findNavController().navigate(R.id.action_pantallaInicio_to_editYUGIOH)
    }

    fun deleteyugioh (position : Int){

        db.collection("YUGIOH").document(YUGIOHList[position].idYUGIOH.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Carta Eliminada", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                YUGIOHList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"No se puedo eliminar la carta", Toast.LENGTH_SHORT).show() }
    }
}