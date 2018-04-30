package com.example.evair.produtos.ui.novoproduto


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.evair.produtos.R
import com.example.evair.produtos.api.ProdutoApi
import com.example.evair.produtos.api.RetrofitClient
import com.example.evair.produtos.model.Produto
import com.example.evair.produtos.ui.listaprodutos.ListaProdutosFragment
import com.example.evair.produtos.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_novo_produto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class NovoProdutoFragment : Fragment() {

    var produto:Produto = Produto("","","",0.00, "")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_novo_produto, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!produto.id.isNullOrEmpty()){
            inputNomeProd?.editText?.setText(produto.nome)
            inputCategoriaProd?.editText?.setText(produto.categoria)
            var str = produto.preco.toString()
            inputPrecoProd?.editText?.setText(str)
            inputImagemProd?.editText?.setText(produto.urlImagem)
            btnDeletar.visibility = View.VISIBLE
        } else {
            btnDeletar.visibility = View.INVISIBLE
        }


        btSalvar.setOnClickListener {

            if(validarCampos()) {

                val api = RetrofitClient.getInstance().create(ProdutoApi::class.java)

                if (produto.id.isNullOrEmpty()) {
                    val produto = Produto(null,
                            inputNomeProd?.editText?.text.toString(),
                            inputCategoriaProd?.editText?.text.toString(),
                            inputPrecoProd.editText?.text.toString()?.toDouble(),
                            inputImagemProd.editText?.text.toString())

                    api.salvar(produto).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                            if (response?.isSuccessful == true) {
                                Toast.makeText(context, R.string.cadastrado, Toast.LENGTH_SHORT).show()
                                limparCampos()
                            } else {
                                Toast.makeText(context, R.string.erroapi, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>?, t: Throwable?) {
                            Log.e("Produto", t?.message)
                        }

                    })
                } else {
                    val produto = Produto(produto.id,
                            inputNomeProd?.editText?.text.toString(),
                            inputCategoriaProd?.editText?.text.toString(),
                            inputPrecoProd.editText?.text.toString()?.toDouble(),
                            inputImagemProd.editText?.text.toString())

                    api.update(produto).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                            if (response?.isSuccessful == true) {
                                Toast.makeText(context, R.string.alterado, Toast.LENGTH_SHORT).show()
                                limparCampos()
                                var activity = context as MainActivity
                                activity.changeFragment(ListaProdutosFragment())
                            } else {
                                Toast.makeText(context, R.string.erroapi, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>?, t: Throwable?) {
                            Log.e("Produto", t?.message)
                        }

                    })
                }
            } else {
                Toast.makeText(context, R.string.preencherProduto, Toast.LENGTH_SHORT).show()
            }
        }

        btnDeletar.setOnClickListener{
            val api = RetrofitClient.getInstance().create(ProdutoApi::class.java)

            if(!produto.id.isNullOrEmpty()){
                api.delete(produto.id!!).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        if (response?.isSuccessful == true) {
                            Toast.makeText(context, R.string.deletado, Toast.LENGTH_SHORT).show()
                            limparCampos()
                            var activity = context as MainActivity
                            activity.changeFragment(ListaProdutosFragment())
                        } else {
                            Toast.makeText(context, R.string.erroapi, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("Produto", t?.message)
                    }

                })
            }
        }
    }

    fun validarCampos(): Boolean{

        var retorno = true

        if(inputNomeProd?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        if(inputCategoriaProd?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        if(inputPrecoProd?.editText?.text.toString().isNullOrEmpty()){
            retorno = false
        }

        return retorno
    }

    fun limparCampos(){
        produto.id = null
        inputNomeProd?.editText?.text?.clear()
        inputCategoriaProd?.editText?.text?.clear()
        inputPrecoProd.editText?.text?.clear()
        inputImagemProd.editText?.text?.clear()
    }


}// Required empty public constructor
