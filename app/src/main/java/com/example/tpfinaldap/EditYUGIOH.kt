package com.example.tpfinaldap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tpfinaldap.viewmodels.EditYUGIOHViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditYUGIOH : Fragment() {

    private lateinit var viewModel: EditYUGIOHViewModel
    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var YUGIOHNuevo: EditText
    private lateinit var MonsterTYPE: EditText

    private lateinit var NewImage: EditText
    private lateinit var descriptionNuevo: EditText

    private lateinit var YUGIOHID: String

    private lateinit var botonSubirDatos: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_yugioh, container, false)

        YUGIOHNuevo = view.findViewById(R.id.YUGIOHNuevo)
        MonsterTYPE = view.findViewById(R.id.MonsterType)

        NewImage = view.findViewById(R.id.NewImage)
        descriptionNuevo = view.findViewById(R.id.descriptionNuevo)

        botonSubirDatos = view.findViewById(R.id.botonSubirDatos)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

        db.collection("YUGIOH").document(data1).get().addOnSuccessListener {

            YUGIOHNuevo.setText(it.data?.get("YUGIOHNEW").toString())
            MonsterTYPE.setText(it.data?.get("Monster Type").toString())
            NewImage.setText(it.data?.get("Image").toString())
            descriptionNuevo.setText(it.data?.get("description").toString())
            YUGIOHID = it.data?.get("IDYUGIOH").toString()

        }.addOnFailureListener {
            Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
        }

        botonSubirDatos.setOnClickListener {

            val YUGIOHNuevo = hashMapOf(
                "YUGIOHNEW" to YUGIOHNuevo.text.toString(),
                "Monster Type" to MonsterTYPE.text.toString(),
                "Image" to NewImage.text.toString(),
                "description" to descriptionNuevo.text.toString(),
                "IDYUGIOH" to YUGIOHID
            )

            db.collection("YUGIOH").document(data1).set(YUGIOHNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                }

            findNavController().navigate(R.id.action_editYUGIOH_to_pantallaInicio)
        } }

        return view
    }
}