package zazueta.daniel.digimind

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import zazueta.daniel.digimind.ui.home.HomeFragment

class AdaptadorTareas: BaseAdapter {
    var context: Context
    var tasks: ArrayList<Recordatorio> = ArrayList<Recordatorio>()


    constructor(contexto:Context, tasks: ArrayList<Recordatorio>){
        this.context = contexto
        this.tasks = tasks

    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.recordatorio, null)

        var task = tasks[position]

        val tv_titulo: TextView = vista.findViewById(R.id.txtNombreRecordatorio)
        val tv_time: TextView = vista.findViewById(R.id.txtTiempoRecordatorio)
        val tv_dia: TextView = vista.findViewById(R.id.txtDiaRecordatorio)

        tv_titulo.setText(task.nombre)
        tv_time.setText(task.tiempo)
        tv_dia.setText(task.dia)

        vista.setOnClickListener{
            eliminar(task)
        }

        return  vista
    }

    fun eliminar(task:Recordatorio){

        val alertDialog:AlertDialog? = context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        //User clicked Ok button
                        HomeFragment.tasks.remove(task)
                        guardar_json()
                        HomeFragment.adaptador.notifyDataSetChanged()
                        Toast.makeText(context, R.string.msg_deleted, Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        //User cancelled the dialog
                    })
            }
            //Set other dialog properties
            builder?.setMessage(R.string.msg).setTitle(R.string.title)

            //Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }
    fun guardar_json(){

        val preferencias = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor =  preferencias?.edit()

        val gson: Gson = Gson()
        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas", json)
        editor?.apply()

    }
}