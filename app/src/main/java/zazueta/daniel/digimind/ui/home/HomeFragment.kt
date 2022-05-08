package zazueta.daniel.digimind.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import zazueta.daniel.digimind.AdaptadorTareas
import zazueta.daniel.digimind.LoginActivity
import zazueta.daniel.digimind.Recordatorio
import zazueta.daniel.digimind.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val db = Firebase.firestore

    companion object{
        var tasks: ArrayList<Recordatorio> = ArrayList<Recordatorio>()
        lateinit var adaptador: AdaptadorTareas
        var first: Boolean = true

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



        if(first){
            Toast.makeText(container?.context, "Bienvenido ${LoginActivity.emailUser}", Toast.LENGTH_SHORT).show()
            cargar_tareas()
            first = false
        }

        val gridView: GridView = binding.gridView

        adaptador = AdaptadorTareas(root.context, tasks)
        gridView.adapter = adaptador

        return root
    }


    fun cargar_tareas(){
        var nombre: String
        var dia: String
        var tiempo: String
        db.collection("actividades")
            .whereEqualTo("user", LoginActivity.emailUser)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(document != null){
                        nombre = document.get("actividad").toString()
                        dia = document.get("dia").toString()
                        tiempo = document.get("tiempo").toString()
                        var tarea: Recordatorio = Recordatorio(nombre, dia, tiempo)
                        tasks.add(tarea)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error al cargar las tareas", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}