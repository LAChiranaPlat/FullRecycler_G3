package com.example.fullrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fullrecycler.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), myAdaptador.OnItemClickListener {

    lateinit var layout:ActivityMainBinding
    var flagLayout=false

    var nItem:Int=0
    lateinit var lista:ArrayList<miEstructura>
    var flagPresentacion=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout= ActivityMainBinding.inflate(layoutInflater)

        setContentView(layout.root)

        val recycler=layout.lista
        val search=layout.txtFind

        lista= ArrayList()
        var cont=1

        if(savedInstanceState != null){
            lista.addAll(savedInstanceState.getParcelableArrayList("lista")!!)
        }else{
            lista.add(miEstructura("00${cont}","Oliver","Developer"))
            cont++
            lista.add(miEstructura("00${cont}","Deyvi","Developer"))
            cont++
            lista.add(miEstructura("00${cont}","Henry","Developer"))
            cont++
            lista.add(miEstructura("00${cont}","Gary","Developer"))
            cont++
            lista.add(miEstructura("00${cont}","Alexander","Developer"))
        }

        if(flagLayout!=true)
            recycler.layoutManager=LinearLayoutManager(this)

        val adaptador=myAdaptador(lista,this)

        recycler.adapter=adaptador

        //CAMBIAR DISTRIBUCION/DISEÑO
        layout.btnChange.setOnClickListener {
            if(flagLayout){
                recycler.layoutManager=LinearLayoutManager(this)
                flagLayout=false
            }else{
                recycler.layoutManager=GridLayoutManager(this,2)
                flagLayout=true
            }

        }

        //AÑADIR ITEMS
        layout.btnAdd.setOnClickListener {
            var name=layout.nameDev.editText?.text.toString()
            var cargo=layout.cargoDev.editText?.text.toString()

            if(name.isEmpty() || cargo.isEmpty()){

                MaterialAlertDialogBuilder(this)
                    .setTitle("Error de Registro")
                    .setMessage("Debe llenar los campos para añadir al registro")
                    .setNeutralButton("Ok"){x,y->
                        layout.nameDev.editText?.requestFocus()
                    }.show()

            }else{

                cont++
                lista.add(miEstructura("00${cont}",name,cargo))
                adaptador.notifyDataSetChanged()

                layout.nameDev.editText?.text?.clear()
                layout.cargoDev.editText?.text?.clear()
                layout.nameDev.editText?.requestFocus()

                Toast.makeText(this,"Usuario registrado correctamente",Toast.LENGTH_LONG).show()

            }

            Log.i("result",lista.toString())
        }

        //AÑADIR ITEMS
        layout.btnRemove.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Eliminar Registro")
                .setMessage("Esta seguro de Eliminar el Registro: ${lista.get(nItem).nombre}")
                .setPositiveButton("Eliminar"){d,w->

                    lista.removeAt(nItem)
                    adaptador.notifyDataSetChanged()


                }.show()

        }

        //BUSCAR
        search.setOnQueryTextListener( object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                adaptador.filtrar(p0.toString())
                return true
            }
        })
    }

    override fun OnItemClick(position: Int) {
        nItem=position
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("lista",lista)

    }



}