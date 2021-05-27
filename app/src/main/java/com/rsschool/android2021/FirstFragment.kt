package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.android2021.model.Data


class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private lateinit var minField: EditText
    private lateinit var maxField: EditText

    private var min: Int = 0
    private var max: Int = 0

    private lateinit var sendMessage: SendMessageResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minField = view.findViewById(R.id.min_value)
        maxField = view.findViewById(R.id.max_value)

        // val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val result = sendMessage.onSendMessageResult()

        previousResult?.text = "Previous result: $result"

        minField = view.findViewById(R.id.min_value)
        maxField = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {

            if ((activity is MainActivity) and (checkInput())) {
                sendMessage.onGetMessageData(Data(min, max))
                (activity as MainActivity).openSecondFragment(min, max)
            }

        }
    }

    private fun checkInput():Boolean {

        var result = false

        try {
            min = minField.text.toString().toInt()
            max = maxField.text.toString().toInt()
        }
        catch (e: Exception){
            Toast.makeText(context, "Repeat please, type error", Toast.LENGTH_LONG).show()
            return false
        }

        if ((min >= 0) && (max >= 0) && (min <= max))
                result = true
        else
            Toast.makeText(context, "Repeat please, add new number for field", Toast.LENGTH_LONG).show()


        return  result
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface SendMessageResult {
        fun onSendMessageResult(): Int
        fun onGetMessageData(data:Data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SendMessageResult)
            sendMessage =  context
        else
            Toast.makeText(context, "Repeat please, first fragment not attach", Toast.LENGTH_LONG).show()
            // throw RuntimeException(context.toString())

    }

}

