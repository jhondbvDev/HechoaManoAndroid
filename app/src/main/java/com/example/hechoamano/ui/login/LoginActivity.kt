package com.example.hechoamano.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.hechoamano.data.authentication.LoginRequest
import com.example.hechoamano.data.authentication.LoginResponse
import com.example.hechoamano.data.network.LoginClient
import com.example.hechoamano.data.session.SessionManager
import com.example.hechoamano.databinding.ActivityLoginBinding
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.home.LoadInventoryActivity
import okhttp3.Credentials
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import java.nio.charset.Charset

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if(binding.user.text.toString().isEmpty() || binding.password.text.toString().isEmpty()){
                Toast.makeText(this, "Por favor diligencia todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login()
        }

        /*if(true) {
            binding.user.setText("Admin")
            binding.password.setText("Admin")
            binding.loginButton.performClick()
        }*/
    }

    private fun login() {
        val apiClient = LoginClient()
        val sessionManager = SessionManager(this)

        val userRequest = LoginRequest(binding.user.text.toString(), binding.password.text.toString())
        val credentials = Credentials.basic(userRequest.user, userRequest.password, Charset.forName("UTF-8"))

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Iniciando sesión...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        apiClient.getLoginService().login(credentials)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Ocurrió un error inesperado. Vuelve a intentarlo.", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()

                    if (loginResponse?.userName != null) {
                        sessionManager.saveAuthToken(loginResponse.token)
                        GoToHome()
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                    }
                    progressDialog.hide()
                }
            })
    }

    private fun GoToHome() {
        val intent = Intent(this, LoadInventoryActivity::class.java)
        startActivity(intent)
    }
}