package com.example.evair.produtos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.evair.produtos.api.LoginApi
import com.example.evair.produtos.api.RetrofitClient
import com.example.evair.produtos.model.Login

import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.content.SharedPreferences
import com.example.evair.produtos.ui.main.MainActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        var prefs: SharedPreferences? = applicationContext.getSharedPreferences("LOGIN",0);

        btnLogin.setOnClickListener{
            val api = RetrofitClient.getInstance().create(LoginApi::class.java)

            var login = Login("","",inputSenha?.editText?.text.toString(), inputEmail?.editText?.text.toString())
            api.login(login).enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                    if (response?.isSuccessful == true) {
                        if(response.body()?.email != null && !response.body()?.email.equals("")) {

                            prefs?.edit()?.putString("LOGIN", inputEmail?.editText?.text.toString())?.commit()
                            prefs?.edit()?.putString("SENHA", inputSenha?.editText?.text.toString())?.commit()
                            prefs?.edit()?.putBoolean("MENTER_LOGADO", true)?.commit()

                            try {
                                val k = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(k)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(applicationContext, R.string.erroemail, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, response?.body().toString(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Login>?, t: Throwable?) {
                    Log.e("PRODUTO", t?.message)
                    Toast.makeText(applicationContext, t?.message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        btnCadastre.setOnClickListener{
            try {
                val k = Intent(this@LoginActivity, CadastroActivity::class.java)
                startActivity(k)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}
