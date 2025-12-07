package com.example.kuchniapolska.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kuchniapolska.R
import com.example.kuchniapolska.databinding.FragmentSummaryBinding
import com.example.kuchniapolska.viewmodel.OrderViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel.order.observe(viewLifecycleOwner) { order ->
            if (order == null) {
                binding.soupSummaryTextView.text = "Brak zamówienia"
                binding.mealSummaryTextView.text = ""
                binding.drinkSummaryTextView.text = ""
                binding.totalPriceTextView.text = "Do zapłaty: 0.00 zł"
                return@observe
            }

            var totalPrice = 0.0

            order.soup?.let {
                val price = orderViewModel.getPrice(it)
                binding.soupSummaryTextView.text = "Zupa: $it (${String.format("%.2f", price)} zł)"
                totalPrice += price
            } ?: run {
                binding.soupSummaryTextView.text = "Zupa: nie wybrano"
            }

            order.meal?.let {
                val price = orderViewModel.getPrice(it)
                binding.mealSummaryTextView.text = "Danie główne: $it (${String.format("%.2f", price)} zł)"
                totalPrice += price
            } ?: run {
                binding.mealSummaryTextView.text = "Danie główne: nie wybrano"
            }

            order.drink?.let {
                val price = orderViewModel.getPrice(it)
                binding.drinkSummaryTextView.text = "Napój: $it (${String.format("%.2f", price)} zł)"
                totalPrice += price
            } ?: run {
                binding.drinkSummaryTextView.text = "Napój: nie wybrano"
            }

            binding.totalPriceTextView.text = "Do zapłaty: ${String.format("%.2f", totalPrice)} zł"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}