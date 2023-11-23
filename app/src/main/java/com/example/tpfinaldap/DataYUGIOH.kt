package com.example.tpfinaldap

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tpfinaldap.viewmodels.YUGIOHViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataYUGIOH : Fragment() {

    private lateinit var viewModel: YUGIOHViewModel
    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var YUGIOHData: TextView
    private lateinit var MonsterTypeData: TextView
    private lateinit var photoData: ImageView
    private lateinit var descriptionData: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data_yugioh, container, false)

        YUGIOHData = view.findViewById(R.id.YUGIOHData)
        MonsterTypeData = view.findViewById(R.id.MonsterTypeData)
        photoData = view.findViewById(R.id.photoData)
        descriptionData = view.findViewById(R.id.descriptionData)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("YUGIOH").document(data1).get().addOnSuccessListener {

                YUGIOHData.text = (it.data?.get("yugioh").toString())
                MonsterTypeData.text = (it.data?.get("monstertype").toString())
                Glide.with(photoData.context).load(it.data?.get("photo").toString()).into(photoData)
                descriptionData.text = (it.data?.get("description").toString())

            }.addOnFailureListener {
                Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}