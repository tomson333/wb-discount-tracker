package com.example.wbagg
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.VH>() {
    private var items = listOf<Product>()
    fun submitList(list: List<Product>) { items = list; notifyDataSetChanged() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return VH(v)
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.title.text = it.title
        holder.subtitle.text = "${'$'}{it.new_price ?: "—"} • ${'$'}{it.discount ?: ""}"
        holder.itemView.setOnClickListener {
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(it.url))
            holder.itemView.context.startActivity(intent)
        }
    }
    class VH(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(android.R.id.text1)
        val subtitle: TextView = view.findViewById(android.R.id.text2)
    }
}
