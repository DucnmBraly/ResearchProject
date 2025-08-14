package com.braly.researchproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
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

        binding.btnClockwise.setOnClickListener {
            binding.phenakistoView.updateRotateDirection(RotateDirection.CLOCKWISE)
        }

        binding.btnCounterClockwise.setOnClickListener {
            binding.phenakistoView.updateRotateDirection(RotateDirection.COUNTER_CLOCKWISE)
        }

        binding.btnStart.setOnClickListener {
            binding.ivPreview.isVisible = false
            binding.phenakistoView.animateStep()
        }

        binding.btnUp.setOnClickListener {
            binding.phenakistoView.decreaseDelay(20L)
        }

        binding.btnDown.setOnClickListener {
            binding.phenakistoView.increaseDelay(20L)
        }

        test()
    }

    private fun test() {
        preloadImage(R.drawable.test5, context = this, onResourceReady = { bitmap ->
            binding.phenakistoView.post {
                val bitmapNe = bitmap.toBitmap()
                val frameCount = 16
                binding.phenakistoView.setPhenakistoScopeImage(
                    bitmap = bitmapNe,
                    frameCount = frameCount
                )
            }
        })

    }
}