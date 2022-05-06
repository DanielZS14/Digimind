package zazueta.daniel.digimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import zazueta.daniel.digimind.databinding.ActivityContrasenaBinding

class ContrasenaActivity : AppCompatActivity() {

    lateinit var binding: ActivityContrasenaBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRestablecer.setOnClickListener {

            var correo = binding.etCorreoCont.text.toString()

            if (!correo.isNullOrBlank()) {

                auth.sendPasswordResetEmail(correo)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se envio un correo a $correo", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

            } else {

                Toast.makeText(this, "Ingresar correo", Toast.LENGTH_SHORT).show()

            }

        }

    }

}