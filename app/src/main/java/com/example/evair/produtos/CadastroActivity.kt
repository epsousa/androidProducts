package com.example.evair.produtos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.evair.produtos.api.LoginApi
import com.example.evair.produtos.api.RetrofitClient
import com.example.evair.produtos.model.Login
import kotlinx.android.synthetic.main.activity_cadastro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnCadastar.setOnClickListener{
            val api = RetrofitClient.getInstance().create(LoginApi::class.java)

            if(!validarDados()){
                if(inputEmailCad?.editText?.text.toString().isNullOrEmpty()){
                    Toast.makeText(applicationContext, R.string.preencher, Toast.LENGTH_SHORT).show()
                } else if (!inputEmailCad?.editText?.text.toString().contains(Regex("(\\w+@\\w+\\.\\w+)(\\.\\w+)?"))) {
                    Toast.makeText(applicationContext, R.string.corrigirEmail, Toast.LENGTH_SHORT).show()
                }
            } else if(inputSenha1?.editText?.text.toString().equals(inputSenha2?.editText?.text.toString())) {


                var login = Login("", inputNomeCad?.editText?.text.toString(), inputSenha1?.editText?.text.toString(), inputEmailCad?.editText?.text.toString())
                api.salvar(login).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        if (response?.isSuccessful == true) {
                            Toast.makeText(applicationContext, R.string.cadastrado, Toast.LENGTH_SHORT).show()
                            try {
                                val k = Intent(this@CadastroActivity, LoginActivity::class.java)
                                startActivity(k)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(applicationContext, R.string.erroapi, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("PRODUTO", t?.message)
                        Toast.makeText(applicationContext, t?.message, Toast.LENGTH_SHORT).show()
                    }

                })
            } else {
                Toast.makeText(applicationContext, R.string.senhasdiferentes, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validarDados(): Boolean{
        var retorno = true

        if(inputNomeCad?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        if(inputEmailCad?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        } else if (!inputEmailCad?.editText?.text.toString().contains(Regex("(\\w+@\\w+\\.\\w+)(\\.\\w+)?"))) {
            retorno = false
        }

        if(inputSenha1?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        if(inputSenha2?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        return retorno
    }
}
