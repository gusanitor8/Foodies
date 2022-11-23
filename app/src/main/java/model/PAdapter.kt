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

class PAdapter(private val providerList: ArrayList<Provider>): RecyclerView.Adapter<PAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.provider_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProvider= providerList[position]
        holder.name.text = currentProvider.Name
        Picasso.get().load(currentProvider.Image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return providerList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.providerTextView)
        var image: ImageView = itemView.findViewById(R.id.providerImageView)
    }
}