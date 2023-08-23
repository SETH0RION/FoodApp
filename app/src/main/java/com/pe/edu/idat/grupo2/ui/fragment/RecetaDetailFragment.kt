package com.pe.edu.idat.grupo2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentRecetaDetailBinding
import com.pe.edu.idat.grupo2.model.Recetas


class RecetaDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecetaDetailBinding
    private val args: RecetaDetailFragmentArgs by navArgs()
    private lateinit var receta : Recetas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receta = args.receta
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecetaDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRecetaName.text = receta.strMeal
        binding.txtRecetaCategory.text = receta.strCategory
        binding.txtRecetaLugar.text = receta.strArea
        binding.txtDetalleReceta.text = receta.strInstructions

        if(receta.strMealThumb.isNotEmpty()){
            binding.imageReceta.load(receta.strMealThumb)
        }
        else{
            binding.imageReceta.setImageResource(R.drawable.prueba_image)
        }
        if (receta.isFavorite){
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }
        else{
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

}