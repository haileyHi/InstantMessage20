package project.instantmessage20

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_chat_room.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ChatRoomActivity: AppCompatActivity() {
    internal lateinit var preferences: SharedPreferences
    private lateinit var chating_text: EditText
    private lateinit var chat_Send_Button: Button

    /*https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ko에 따라 넣은 부분*/
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager



    var arrayList = arrayListOf<ChatModel>()
    var mAdapter = ChatAdapter(this, arrayList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat_room)
        preferences = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)

        /* 다음 두 줄도.*/
        viewManager = LinearLayoutManager(this)
        //viewAdapter = ChatAdapter(myDataset)


        //어댑터 지정
        chat_recycler_view.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        chat_recycler_view.layoutManager = lm
        chat_recycler_view.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        //chat_Send_Button = findViewById(R.id.chat_Send_Button)
        //chating_text = findViewById(R.id.chating_text)

        chat_Send_Button.setOnClickListener{
            sendMessage()
        }

    }

    fun sendMessage() {
        val now = System.currentTimeMillis()
        val date = Date(now)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val stf = SimpleDateFormat("HH:mm")

        val getDate = sdf.format(date) //날짜
        val getTime = stf.format(date) //시간

        val item = ChatModel(preferences.getString("name", ""), chating_text.text.toString(), "example", getTime)
        mAdapter.addItem(item)
        mAdapter.notifyDataSetChanged()

        chating_text.setText("")

    }
}

