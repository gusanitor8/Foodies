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

class TAdapter(private val timeList: ArrayList<Time>): RecyclerView.Adapter<TAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTime = timeList[position]
        holder.name.text = currentTime.Name
        Picasso.get().load(currentTime.Image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.timeTextView)
        var image: ImageView = itemView.findViewById(R.id.timeImageView)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}