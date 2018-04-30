package com.example.evair.produtos.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.evair.produtos.LoginActivity
import com.example.evair.produtos.R
import com.example.evair.produtos.fragment_sobre
import com.example.evair.produtos.ui.listaprodutos.ListaProdutosFragment
import com.example.evair.produtos.ui.novoproduto.NovoProdutoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lista -> {
                changeFragment(ListaProdutosFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_novo -> {
                changeFragment(NovoProdutoFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sobre -> {
                changeFragment(fragment_sobre())
                return@OnNavigationItemSelectedListener true
            }
            R.id.loggout -> {
                var prefs: SharedPreferences? = applicationContext.getSharedPreferences("LOGIN",0);
                try {
                    prefs?.edit()?.putBoolean("MENTER_LOGADO", false)?.commit()
                    val k = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(k)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        changeFragment(ListaProdutosFragment())

    }

}
