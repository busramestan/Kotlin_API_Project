package com.busramestan.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.busramestan.project.R
import com.busramestan.project.databinding.ListRowBinding
import com.busramestan.project.model.UserData
import kotlinx.android.synthetic.main.list_row.view.*

class ProjectAdapter(val userDataList : ArrayList<UserData>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(var viewBinding :ListRowBinding) : RecyclerView.ViewHolder(viewBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
      //layout(list_row) ile adapteri biribirne bağla
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListRowBinding>(LayoutInflater.from(parent.context), R.layout.list_row, parent, false)
        return ProjectViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {

        holder.viewBinding.deathText.text = userDataList[position].death.toString()
        holder.viewBinding.killText.text=userDataList[position].kill.toString()
        holder.viewBinding.loseText.text=userDataList[position].lose.toString()
        holder.viewBinding.nameText.text=userDataList[position].nickName
        holder.viewBinding.winText.text= userDataList[position].win.toString()
        holder.viewBinding.scoreText.text=userDataList[position].score.toString()

    }

    override fun getItemCount(): Int {
        return userDataList.size
        //kaç tane row ?
    }


    fun updateUserDataList( newUserDataList : List<UserData> ){
        userDataList.clear()
        userDataList.addAll(newUserDataList)
        notifyDataSetChanged()
    }

}