package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.android2021.model.Data
import kotlin.random.Random

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    private lateinit var sendMessage: GetMessageResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

       // val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
       // val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val data = sendMessage.onSendMessageData()

        result?.text = generate(data.min, data.max).toString()
        sendMessage.onGetMessageResult(result!!.text.toString().toInt())

        backButton?.setOnClickListener {
            sendFragmentResult(result!!.text.toString().toInt())
        }

    }

    private fun sendFragmentResult(res: Int) {
        if (activity is MainActivity) {
            (activity as MainActivity).openFirstFragment(res)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

    interface GetMessageResult{
        fun onGetMessageResult(result: Int)
        fun onSendMessageData(): Data
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GetMessageResult)
            sendMessage =  context
        else
            Toast.makeText(context, "Repeat please, second fragment not attach", Toast.LENGTH_LONG).show()
            // throw RuntimeException(context.toString())

    }


}