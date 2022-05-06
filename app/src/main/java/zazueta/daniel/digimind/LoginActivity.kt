package zazueta.daniel.digimind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import zazueta.daniel.digimind.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrarse.setOnClickListener {
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        binding.tvOlvidasteContra.setOnClickListener {
            val intent: Intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intent)
        }

        binding.btnIngresar.setOnClickListener {
            validaIngreso()
        }

    }

    fun validaIngreso() {
        var correo = binding.etCorreo.text.toString()
        var contra = binding.etContra.text.toString()


        if (!correo.isNullOrBlank() && !contra.isNullOrBlank()) {

            ingresarFirebase(correo, contra)

        } else {
            Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ingresarFirebase(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser

                    val intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

}