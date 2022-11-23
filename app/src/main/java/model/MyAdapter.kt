package model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.R
import com.squareup.picasso.Picasso
import java.nio.file.attribute.UserPrincipalLookupService

class MyAdapter(private val menuList: ArrayList<Menu>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMenu = menuList[position]
        holder.name.text = currentMenu.Name
        holder.price.text = "Q" + currentMenu.Price
        Picasso.get().load(currentMenu.Image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.menuTextView)
        val price: TextView = itemView.findViewById(R.id.priceTextView)
        var image: ImageView = itemView.findViewById(R.id.menuImageView)
    }
}