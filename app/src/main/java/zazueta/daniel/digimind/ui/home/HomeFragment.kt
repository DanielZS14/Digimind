package zazueta.daniel.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import zazueta.daniel.digimind.AdaptadorTareas
import zazueta.daniel.digimind.Carrito
import zazueta.daniel.digimind.Recordatorio
import zazueta.daniel.digimind.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        var tasks: ArrayList<Recordatorio> = ArrayList<Recordatorio>()
        var first = true
        lateinit var adaptador: AdaptadorTareas
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val gridView: GridView = binding.gridView


        /*if(first){
            fill_tasks()
            first = false
        }*/

        cargar_tareas()

        adaptador = AdaptadorTareas(root.context, tasks)

        gridView.adapter = adaptador

        return root
    }

    fun fill_tasks(){
        tasks.add(Recordatorio("tarea 1", "Lunes", "15:00"))
        tasks.add(Recordatorio("tarea 2", "Martes", "15:00"))
        tasks.add(Recordatorio("tarea 3", "Miercoles", "15:00"))
        tasks.add(Recordatorio("tarea 4", "Jueves", "15:00"))
        tasks.add(Recordatorio("tarea 5", "Viernes", "15:00"))
    }

    fun cargar_tareas(){

        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()

        var json = preferencias?.getString("tareas", null)

        val type = object : TypeToken<ArrayList<Recordatorio?>?>() {}.type

        if(json == null){
            tasks = ArrayList<Recordatorio>()
        }else {
            tasks = gson.fromJson(json, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}