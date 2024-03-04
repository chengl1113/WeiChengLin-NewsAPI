package com.example.weichenglin_simplenewsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weichenglin_simplenewsapp.api.NewsApi
import com.example.weichenglin_simplenewsapp.databinding.FragmentNewsGalleryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create

private const val TAG = "NewsGalleryFragment"
class NewsGalleryFragment() : Fragment() {
    private var _binding: FragmentNewsGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentNewsGalleryBinding.inflate(inflater, container, false)
        binding.newsGrid.layoutManager = GridLayoutManager(context, 1)
        return binding.root

    }

    private val newsGalleryViewModel: NewsGalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner : Spinner = requireActivity().findViewById(R.id.category_spinner)
        Log.d(TAG, "Spinner: $spinner")
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = spinner.selectedItem.toString().lowercase()
                Log.d(TAG, "SELECTED $selected")
                newsGalleryViewModel.setQuery(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                newsGalleryViewModel.galleryItems.collect { items ->
//                    Log.d(TAG, "Response received: $items")
                    binding.newsGrid.adapter = NewsListAdapter(items) { title, url, content ->
                        findNavController().navigate(
                            NewsGalleryFragmentDirections.showNewsDetail(title, url, content)
                        )
                    }
                }
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


