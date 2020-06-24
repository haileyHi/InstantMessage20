package project.instantmessage20

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.your_chat_item.view.*

class ChatAdapter(val context: Context, val arrayList: ArrayList<ChatModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal lateinit var preferences: SharedPreferences

    fun addItem(item: ChatModel) {
        if (arrayList != null) {
            arrayList.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view: View

        //getItemViewType 에서 뷰타입 1을 리턴받았다면 내 채팅 레이아웃을 받은 Holder를 리턴
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.my_chat_item, parent, false)
            return HolderMe(view)
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.your_chat_item, parent, false)
            return HolderPerson(view)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if(viewHolder is HolderMe){
            (viewHolder as HolderMe).chat_text?.setText(arrayList.get(i).script)
            (viewHolder as HolderMe).chat_time?.setText(arrayList.get(i).date_time)
        }
        else if(viewHolder is HolderPerson){
            (viewHolder as HolderPerson).chat_user_image?.setImageResource(R.mipmap.ic_launcher)//상대의 이미지 받아와서 넣어주기
            (viewHolder as HolderPerson).chat_friend_name?.setText(arrayList.get(i).name)
            (viewHolder as HolderPerson).chat_text?.setText(arrayList.get(i).script)
            (viewHolder as HolderPerson).chat_time?.setText(arrayList.get(i).date_time)
        }
    }

    inner class HolderMe(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chat_text = itemView?.findViewById<TextView>(R.id.chat_textBox)
        val chat_time = itemView?.findViewById<TextView>(R.id.chat_time)
    }

    inner class HolderPerson(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val chat_user_image = itemView?.findViewById<ImageView>(R.id.user_image)
        val chat_friend_name = itemView?.findViewById<TextView>(R.id.chat_friend_name)
        val chat_text = itemView?.findViewById<TextView>(R.id.chat_textBox)
        val chat_time = itemView?.findViewById<TextView>(R.id.chat_time)
    }

    override fun getItemViewType(position: Int): Int {
        preferences = context.getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)

        return if (arrayList.get(position).name == preferences.getString("name", "")) {
            1
        } else {
            2
        }
    }
}


