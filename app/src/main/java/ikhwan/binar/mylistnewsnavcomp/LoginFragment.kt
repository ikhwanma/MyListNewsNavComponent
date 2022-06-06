package ikhwan.binar.mylistnewsnavcomp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import ikhwan.binar.mylistnewsnavcomp.model.user.GetUserResponseItem
import ikhwan.binar.mylistnewsnavcomp.network.ApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.btn_register
import kotlinx.android.synthetic.main.fragment_login.input_password
import kotlinx.android.synthetic.main.fragment_login.input_username
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            requireActivity().getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedPreferences.contains(USERNAME)) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
        }

        btn_login.setOnClickListener{
            val uname = input_username.text.toString()
            val pass = input_password.text.toString()

            if (uname != "" && pass != ""){
                val views = LayoutInflater.from(requireContext()).inflate(R.layout.wait_dialog, null, false)
                val dialog = AlertDialog.Builder(requireContext()).setView(views).create()
                dialog.show()
                getData(uname,pass, dialog)
            }else{
                Toast.makeText(requireContext(), "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

        }
        btn_register.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun getData(uname: String, pass: String, dialog: AlertDialog) {
        ApiClient.instance.getAllUser().enqueue(object : Callback<List<GetUserResponseItem>>{
            override fun onResponse(
                call: Call<List<GetUserResponseItem>>,
                response: Response<List<GetUserResponseItem>>
            ) {
                if(response.isSuccessful){
                    cekData(response.body(), uname, pass, dialog)
                }
            }

            override fun onFailure(call: Call<List<GetUserResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun cekData(
        listUser: List<GetUserResponseItem>?,
        uname: String,
        pass: String,
        dialog: AlertDialog
    ) {

        var cek = false
        var username = ""

        for (data in listUser!!){
            if (data.username == uname && data.password == pass){
                cek = true
                username = data.username
                break
            }
        }
        dialog.dismiss()

        if (cek){
            val editor = sharedPreferences.edit()
            editor.putString(USERNAME, username)
            editor.apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
        }else{
            Toast.makeText(requireContext(), "Email atau password salah", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PREF_USER = "user_preference"
        const val USERNAME = "username"
    }
}