package com.example.fullrecycler

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fullrecycler.databinding.ItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.w3c.dom.Text

class myAdaptador(var lista:ArrayList<miEstructura>, var listen:OnItemClickListener):RecyclerView.Adapter<myAdaptador.contenedorVistas>() {

    lateinit var ctx:Context
    var listaOriginal:ArrayList<miEstructura> =ArrayList()

    init {
        listaOriginal.addAll(lista)
    }

    inner class contenedorVistas(views: ItemBinding):RecyclerView.ViewHolder(views.root), View.OnClickListener {

        val codigo:TextView
        val nombre:TextView
        val cargo:TextView
        val content:ConstraintLayout


        init {
            codigo=views.txtCodigo
            nombre=views.txtNombre
            cargo=views.txtCargo
            content=views.root

            content.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listen.OnItemClick(adapterPosition)
        }

    }

    interface OnItemClickListener {

        fun OnItemClick(position: Int)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): myAdaptador.contenedorVistas {

        ctx=parent.context

        val layout=ItemBinding.inflate(LayoutInflater.from(ctx),parent,false)

        layout.txtNombre.setOnClickListener {
            Log.i("result",layout.txtNombre.text.toString())

            ctx.startActivity(Intent(ctx,details::class.java))

        }

        return contenedorVistas(layout)

    }

    override fun onBindViewHolder(holder: myAdaptador.contenedorVistas, position: Int) {

        val item=lista.get(position)


        holder.apply {
            nombre.text=item.nombre
            cargo.text=item.cargo
            codigo.text=item.codigo
        }

       /* holder.content.setOnClickListener {

            MaterialAlertDialogBuilder(ctx)
                .setTitle("Eliminar Registro")
                .setMessage("Esta seguro de Eliminar el Registro: ${holder.nombre.text}")
                .setPositiveButton("Eliminar"){d,w->

                    lista.removeAt(position)
                    notifyDataSetChanged()

                }.show()

        }*/

    }

    override fun getItemCount()=lista.size

    fun filtrar(q:String){

        if(q.isEmpty()){
            lista.clear()
            lista.addAll(listaOriginal)
        }else{

            var resultFiltro:ArrayList<miEstructura> = ArrayList()

            for(item in lista){
                if(item.nombre?.lowercase()!!.contains(q.lowercase())){
                    resultFiltro.add(item)
                }
            }

            lista.clear()
            lista.addAll(resultFiltro)

        }

        notifyDataSetChanged()

    }

}