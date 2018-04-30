package com.example.evair.produtos.ui.listaprodutos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.evair.produtos.R
import com.example.evair.produtos.model.Produto
import com.example.evair.produtos.ui.main.MainActivity
import com.example.evair.produtos.ui.novoproduto.NovoProdutoFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_produto.view.*


class ListaProdutosAdapter(private val produtos: List<Produto>, private val context: Context) : RecyclerView.Adapter<ListaProdutosAdapter.MeuViewHolder>() {

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: MeuViewHolder?, position: Int) {
        val produto = produtos[position]

        holder?.let {
            it.bindView(produto, context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeuViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_produto, parent, false)

        return MeuViewHolder(view)
    }

    class MeuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(produto: Produto, context: Context) {

            var activity = context as MainActivity

            itemView.setOnClickListener{
                var frag = NovoProdutoFragment()
                frag.produto = produto
                activity.changeFragment(frag)
            }
            itemView.tvNome.text = produto.nome
            itemView.tvPreco.text = produto.preco.toString()
            if(!produto.urlImagem.isNullOrEmpty()) {
                Picasso.get().load(produto.urlImagem).placeholder(R.drawable.bag).error(R.drawable.erro).into(itemView.ivFoto);
            } else {
                Picasso.get().load("https://w3.siemens.com.br/automation/br/pt/gerenciamento-vida-produto/solucoes-plm-produtos/PublishingImages/plm-produtos.jpg").placeholder(R.drawable.bag).error(R.drawable.erro).into(itemView.ivFoto);
            }
        }
    }
}