package com.a7medkenawy.elmarket.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.a7medkenawy.elmarket.R


class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog

    fun showCustomToast() {
        val inflater = layoutInflater
        val layout: View =
            inflater.inflate(
                R.layout.customtoast,
                requireActivity().findViewById(R.id.toast_layout_root)
            )

        val toast = Toast(requireContext())
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    fun showProgressDialog() {
        mProgressDialog = Dialog(requireContext())

        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }


}