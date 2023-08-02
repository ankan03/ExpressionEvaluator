package com.example.expressionevaluator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expressionevaluator.models.Expression

class RecyclerViewAdapter(var expressionList: List<Expression>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expression: TextView = itemView.findViewById<TextView>(R.id.text_view_item_expression)
        private val result: TextView = itemView.findViewById<TextView>(R.id.text_view_item_result)

        fun bind(data:Expression){
            expression.text = data.inputExpression
            result.text = "= ${data.outputResult}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expression_result_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.bind(expressionList[position])
    }

    override fun getItemCount(): Int = expressionList.size
}