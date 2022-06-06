package ikhwan.binar.mylistnewsnavcomp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ikhwan.binar.mylistnewsnavcomp.model.GetNewsResponseItem
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = arguments?.getParcelable<GetNewsResponseItem>(HomeFragment.EXTRA_NEWS) as GetNewsResponseItem

        val title = news.title
        val image = news.image
        val desc = news.description
        val author = news.author
        val date = news.createdAt
        var tempDate = ""
        for (i in 0..9){
            tempDate += date[i]
        }
        val txtDate = convertDate(tempDate)
        tv_date.text = convertDate(tempDate)
        val txtAuthor = "Author : $author"

        Glide.with(requireView()).load(image).into(img_news)
        tv_title.text = title
        tv_date.text = txtDate
        tv_author.text = txtAuthor
        tv_news.text = desc

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