package com.braly.researchproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.braly.researchproject.databinding.ActivityMainBinding
import com.braly.researchproject.databinding.ActivitySplineBinding
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.launch

class SplineActivity : AppCompatActivity() {
    var _binding: ActivitySplineBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivitySplineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        test()
    }

    private fun test() {
        val url ="https://build.spline.design/r7vufRzuTnbHle7G/scene.splinecontent"
        binding.sceneView.loadUrl(url)
    }
}