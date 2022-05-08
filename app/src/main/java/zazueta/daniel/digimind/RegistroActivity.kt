package zazueta.daniel.digimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import zazueta.daniel.digimind.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrar.setOnClickListener {
            validaRegistro()
        }
    }


    private fun validaRegistro() {
        val correo = binding.etCorreoReg.text.toString()
        val contra1 = binding.etContraReg.text.toString()
        val contra2 = binding.etContra2Reg.text.toString()

        if (!correo?.isNullOrBlank() && !contra1?.isNullOrBlank() && !contra2?.isNullOrBlank()) {

            if (contra1 == contra2) {
                registrarFirebase(correo, contra1)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "${user?.email} se ha creado correctamente ",
                        Toast.LENGTH_SHORT
                    ).show()
                    agregarActividad(email, password)

                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }

    private fun agregarActividad(email: String, password: String) {
        val user = hashMapOf(
            "email" to email,
            "clave" to password,
        )
        db.collection("users").document(email)
            .set(user)
    }

}