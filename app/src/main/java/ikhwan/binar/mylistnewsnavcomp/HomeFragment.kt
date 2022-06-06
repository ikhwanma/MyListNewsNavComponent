package ikhwan.binar.mylistnewsnavcomp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ikhwan.binar.mylistnewsnavcomp.model.GetNewsResponseItem
import ikhwan.binar.mylistnewsnavcomp.network.ApiClient
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            requireActivity().getSharedPreferences(LoginFragment.PREF_USER, Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = sharedPreferences.getString(LoginFragment.USERNAME, "")
        val txtUname = "Helo, $username!"
        tv_hallo.text = txtUname
        ApiClient.instance.getAllNews().enqueue(object : Callback<List<GetNewsResponseItem>>{
            override fun onResponse(
                call: Call<List<GetNewsResponseItem>>,
                response: Response<List<GetNewsResponseItem>>
            ) {
                if (response.isSuccessful){
                    progress_circular.visibility = View.GONE
                    Toast.makeText(requireContext(), "Success getting data", Toast.LENGTH_SHORT).show()
                    showNews(response.body())
                }
            }

            override fun onFailure(call: Call<List<GetNewsResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

        btn_logout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Logout")
                .setMessage("Are you sure?")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("Yes") { _, _ ->
                    sharedPreferences.edit().clear().apply()
                    it.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("No") { _, _ ->

                }
                .show()
        }
    }

    private fun showNews(news: List<GetNewsResponseItem>?) {
        rv_news.layoutManager = LinearLayoutManager(requireContext())
        rv_news.adapter = NewsAdapter(news!!){
            val mBundle = bundleOf(EXTRA_NEWS to it)
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
        }

    }

    companion object{
        const val EXTRA_NEWS = "extra_news"
    }

}