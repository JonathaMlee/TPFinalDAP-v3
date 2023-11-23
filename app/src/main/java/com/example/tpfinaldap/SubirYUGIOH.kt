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
import androidx.navigation.fragment.findNavController
import com.example.tpfinaldap.viewmodels.SubirYUGIOHViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SubirYUGIOH : Fragment() {

    companion object {
        fun newInstance() = SubirYUGIOH()
    }

    private lateinit var viewModel: SubirYUGIOHViewModel
    private lateinit var TextYUGIOH: EditText
    private lateinit var TextMonsterType: EditText
    private lateinit var textFoto: EditText
    private lateinit var textDescription: EditText
    private var db = Firebase.firestore
    private lateinit var botonSubir: Button

    private lateinit var dataYUGIOH: String




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_subir_yugioh, container, false)

        TextYUGIOH = view.findViewById(R.id.TextYUGIOH)
        TextMonsterType = view.findViewById(R.id.TextMonsterType)
        textFoto = view.findViewById(R.id.textFoto)
        textDescription = view.findViewById(R.id.textDescription)
        botonSubir = view.findViewById(R.id.botonSubir)

        botonSubir.setOnClickListener {

            val documentId:String = db.collection("YUGIOH").document().id

            val YUGIOHNuevo = hashMapOf(
                "yugioh" to TextYUGIOH.text.toString(),
                "monstertype" to TextMonsterType.text.toString(),
                "photo" to textFoto.text.toString(),
                "description" to textDescription.text.toString(),
                "idYUGIOH" to documentId
            )

            db.collection("YUGIOH").document(documentId).set(YUGIOHNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir",Toast.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_subirYUGIOH_to_pantallaInicio)
        }

        return view
    }

}