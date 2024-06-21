package com.example.sepic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.sepic.R
import com.example.sepic.Room.ItemDatabase
import com.example.sepic.Room.ItemViewModel
import com.example.sepic.Room.ItemViewModelFactory
import com.example.sepic.UpdateItemRoomActivity

class PopUp(private val itemDatabase: ItemDatabase,  private val position: Int) : DialogFragment() {

    private lateinit var itemViewModel: ItemViewModel

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onStart() {
        super.onStart()
        requireDialog().window?.apply {
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(16, 16, 16, 16)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pop_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ItemViewModelFactory.getInstance(requireContext())
        itemViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]

        val btnUbah: Button = view.findViewById(R.id.btn_ubah)
        val btnHapus: Button = view.findViewById(R.id.btn_hapus)

        btnUbah.setOnClickListener {
            val intent = Intent(requireContext(), UpdateItemRoomActivity::class.java)
            intent.putExtra("post", itemDatabase)
            startActivity(intent)
            dismiss()
        }

        btnHapus.setOnClickListener {
            itemViewModel.deleteItem(itemDatabase)
            dismiss()
        }
    }

    companion object {
        const val TAG = "PopUp"
    }
}
