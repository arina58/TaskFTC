package com.example.taskftc.domain

import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskftc.InfoUserActivity
import com.example.taskftc.R
import com.example.taskftc.data.models.UserRead
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class UserAdapter(private val mList: ArrayList<UserRead>, private val context: Context) :  RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private val selectedTabPositions: SparseArray<Int> = SparseArray()
    companion object {
        const val PAYLOAD_TAB_SELECTION = "PAYLOAD_TAB_SELECTION"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.info.text = itemsViewModel.name
        Picasso.get()
            .load(itemsViewModel.photo)
            .transform(TransformImage())
            .into(holder.photo)

        setUpTabLayoutClickListener(holder.tab, position)
        holder.tab.getTabAt(selectedTabPositions[position] ?: 0)?.select()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, InfoUserActivity::class.java)

            intent.putExtra("user", itemsViewModel)
            context.startActivity(intent)
        }
    }
    private fun setUpTabLayoutClickListener(tabLayout: TabLayout, position: Int) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTabPositions.put(position, tab?.position ?: 0)
                notifyItemChanged(position, PAYLOAD_TAB_SELECTION)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_TAB_SELECTION)) {
            val selectedPosition = selectedTabPositions[position] ?: 0
            val user = mList[position]
            when (selectedPosition) {
                0 -> holder.info.text = user.name
                1 -> holder.info.text = user.phone
                2 -> holder.info.text = user.location
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val info: TextView = itemView.findViewById(R.id.info)
        val photo: ImageView = itemView.findViewById(R.id.Photo)
        val tab: TabLayout = itemView.findViewById(R.id.Tab)

    }
}