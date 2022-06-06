package ikhwan.binar.mylistnewsnavcomp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ikhwan.binar.mylistnewsnavcomp.model.GetNewsResponseItem
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    private val listFilm: List<GetNewsResponseItem>,
    val onItemClick: (GetNewsResponseItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listFilm[position]

        holder.itemView.apply {
            tv_title.text = data.title
            tv_author.text = data.author
            var tempDate = ""
            for (i in 0..9){
                tempDate += data.createdAt[i]
            }
            tv_date.text = convertDate(tempDate)
            rootView.setOnClickListener {
                onItemClick(data)
            }
            this@NewsAdapter.let {
                Glide.with(holder.itemView)
                    .load(data.image)
                    .into(holder.itemView.img_news)
            }
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

    private fun convertDate(data: String): String {
        if (data != "") {
            val list = data.split("-").toTypedArray()
            val day = list[2]
            var month = ""
            val year = list[0]

            when {
                list[1] == "01" -> {
                    month = "Jan"
                }
                list[1] == "02" -> {
                    month = "Feb"
                }
                list[1] == "03" -> {
                    month = "Mar"
                }
                list[1] == "04" -> {
                    month = "Apr"
                }
                list[1] == "05" -> {
                    month = "May"
                }
                list[1] == "06" -> {
                    month = "Jun"
                }
                list[1] == "07" -> {
                    month = "Jul"
                }
                list[1] == "08" -> {
                    month = "Aug"
                }
                list[1] == "09" -> {
                    month = "Sep"
                }
                list[1] == "10" -> {
                    month = "Oct"
                }
                list[1] == "11" -> {
                    month = "Nov"
                }
                list[1] == "12" -> {
                    month = "Dec"
                }
            }
            return "$month $day, $year"
        }
        return ""
    }
}