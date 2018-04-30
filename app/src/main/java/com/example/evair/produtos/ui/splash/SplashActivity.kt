package com.example.evair.produtos.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import com.example.evair.produtos.LoginActivity
import com.example.evair.produtos.R
import com.example.evair.produtos.api.LoginApi
import com.example.evair.produtos.api.RetrofitClient
import com.example.evair.produtos.model.Login
import com.example.evair.produtos.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        var prefs: SharedPreferences? = applicationContext?.getSharedPreferences("LOGIN",0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        carregar()
        if(prefs != null) {
            if (prefs.getBoolean("MENTER_LOGADO", false)) {
                val api = RetrofitClient.getInstance().create(LoginApi::class.java)
                var login = Login("","",prefs!!.getString("SENHA", null), prefs!!.getString("LOGIN", null))
                api.login(login).enqueue(object : Callback<Login> {
                    override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                        if (response?.isSuccessful == true) {
                            if (response.body()?.email != null && !response.body()?.email.equals("")) {
                                try {
                                    val k = Intent(this@SplashActivity, MainActivity::class.java)
                                    startActivity(k)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            } else {
                                Handler().postDelayed({
                                    prefs.edit().putBoolean("MENTER_LOGADO", false).commit()
                                    val k = Intent(this@SplashActivity, LoginActivity::class.java)
                                    startActivity(k)
                                }, 3000)
                            }
                        } else {
                            Handler().postDelayed({
                                prefs.edit().putBoolean("MENTER_LOGADO", false).commit()
                                val k = Intent(this@SplashActivity, LoginActivity::class.java)
                                startActivity(k)
                            }, 3000)
                        }
                    }
                    override fun onFailure(call: Call<Login>?, t: Throwable?) {
                        Log.e("Produto", t?.message)
                        Handler().postDelayed({
                            prefs.edit().putBoolean("MENTER_LOGADO", false).commit()
                            val k = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(k)
                        }, 3000)
                    }
                })
            }
        }

        val api = RetrofitClient.getInstance().create(LoginApi::class.java)

        var login = Login("","",inputSenha?.editText?.text.toString(), inputEmail?.editText?.text.toString())

    }

    fun carregar() {
        val animacao = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)

        ivLogoSplash.startAnimation(animacao)

        Handler().postDelayed({
            var prefs: SharedPreferences? = applicationContext?.getSharedPreferences("LOGIN",0)
            if(!prefs!!.getBoolean("MENTER_LOGADO", false)) {
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }
        }, 3000)
    }
}
