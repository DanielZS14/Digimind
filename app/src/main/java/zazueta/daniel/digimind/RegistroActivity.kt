package zazueta.daniel.digimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import zazueta.daniel.digimind.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrar.setOnClickListener {
            validaRegistro()
        }
    }


    fun validaRegistro(){
        var correo = binding.etCorreoReg.text.toString()
        var contra1 = binding.etContraReg.text.toString()
        var contra2 = binding.etContra2Reg.text.toString()

        if(!correo.isNullOrBlank() && !contra1.isNullOrBlank() && !contra2.isNullOrBlank()){

            if(contra1 == contra2){
                registrarFirebase(correo, contra1)
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "${user?.email} se ha creado correctamente ",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}