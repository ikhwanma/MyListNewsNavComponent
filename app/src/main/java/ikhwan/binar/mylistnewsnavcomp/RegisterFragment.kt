package ikhwan.binar.mylistnewsnavcomp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import ikhwan.binar.mylistnewsnavcomp.model.user.GetUserResponseItem
import ikhwan.binar.mylistnewsnavcomp.model.user.PostUserResponse
import ikhwan.binar.mylistnewsnavcomp.network.ApiClient
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btn_register
import kotlinx.android.synthetic.main.fragment_register.input_username
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {
            val name = input_nama.text.toString()
            val uname = input_username.text.toString()
            val pass = input_password.text.toString()
            val konf = input_konf_password.text.toString()

            if (name != "" && uname != "" && pass != "" && konf != ""){
                if (pass == konf){
                    val views = LayoutInflater.from(requireContext()).inflate(R.layout.wait_dialog, null, false)
                    val dialog = AlertDialog.Builder(requireContext()).setView(views).create()
                    dialog.show()
                    getData(name, uname, pass, dialog)
                }else{
                    Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }else{
                Toast.makeText(requireContext(), "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

    }

    private fun getData(name: String, uname: String, pass: String, dialog: AlertDialog) {
        ApiClient.instance.getAllUser().enqueue(object : Callback<List<GetUserResponseItem>>{
            override fun onResponse(
                call: Call<List<GetUserResponseItem>>,
                response: Response<List<GetUserResponseItem>>
            ) {
                if (response.isSuccessful){
                    cekData(name, uname, pass, response.body(), dialog)
                }
            }

            override fun onFailure(call: Call<List<GetUserResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun cekData(
        name: String,
        uname: String,
        pass: String,
        users: List<GetUserResponseItem>?,
        dialog: AlertDialog
    ) {
        var cek = true

        val user = PostUserResponse("", "", name, pass, 0, uname)

        for(data in users!!){
            if (uname == data.username){
                cek = false
                break
            }
        }

        dialog.dismiss()

        if (cek){
            ApiClient.instance.addUsers(user).enqueue(object : Callback<GetUserResponseItem>{
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
                        Toast.makeText(requireContext(), "Berhasil mendaftarkan akun", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }else{
            Toast.makeText(requireContext(), "Username sudah terdaftar", Toast.LENGTH_SHORT).show()
        }
    }
}