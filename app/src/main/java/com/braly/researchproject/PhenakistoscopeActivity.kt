package com.braly.researchproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.braly.researchproject.databinding.ActivityPhenakistoBinding

class PhenakistoscopeActivity : AppCompatActivity() {
    var _binding: ActivityPhenakistoBinding? = null
    val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityPhenakistoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.root.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        test()


    }

    private fun test() {
        preloadImage(R.drawable.test5, context = this, onResourceReady = { bitmap ->
            binding.spinning3DView.post {
                val bitmapNe = bitmap.toBitmap()
                val frameCount = 16
                binding.spinning3DView.setPhenakistoScopeImage(
                    bitmap = bitmapNe,
                    frameCount = frameCount
                )
                binding.spinning3DView.animateStep()
            }
        })

    }
}