package bwf.idat.pruebas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bwf.idat.pruebas.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            login(username, password)

        }
    }
    private fun login(username: String, password: String) {
        val url = "https://inventory-store-production.up.railway.app/login/login"

        val formBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                println(responseBody)
                runOnUiThread {
                    if (response.isSuccessful && responseBody == "true") {
                        showToast("Credenciales correctas")
                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // Manejar respuesta de error aquí
                        showToast("Usuario o contraseña incorrecto")
                    }
                }
            }
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}