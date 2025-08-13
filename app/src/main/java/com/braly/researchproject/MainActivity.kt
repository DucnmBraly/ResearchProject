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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        test3()

        binding.btnSpline.setOnClickListener {
            val intent = Intent(this, SplineActivity::class.java)
            startActivity(intent)
        }

        binding.btnPhenikisto.setOnClickListener {
            val intent = Intent(this, PhenakistoscopeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun test() {
        val url =
            "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/main/2.0/Avocado/glTF/Avocado.gltf"
        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        val intentUri =
            Uri.parse("https://arvr.google.com/scene-viewer/1.2")
                .buildUpon()
                .appendQueryParameter("file", url)
                .build()
        sceneViewerIntent.setData(intentUri)
        try {
            startActivity(sceneViewerIntent)
        } catch (e: ActivityNotFoundException) {
            // There is no activity that could handle the intent.
        }

    }

    private fun test2() {
        val url =
            "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/main/2.0/Avocado/glTF/Avocado.gltf"
        lifecycleScope.launch {
            val modelInstance = binding.sceneView.modelLoader.loadModelInstance(url)
            modelInstance?.let {
                val node = io.github.sceneview.node.ModelNode(
                    modelInstance = it,
                    // tự scale để chiều dài lớn nhất ≈ 1 đơn vị (tuỳ bạn chỉnh)
                    scaleToUnits = 0.5f
                )
                binding.sceneView.addChildNode(node)
            }

        }

    }

    private fun test3() {
        val url = "test2.glb"
        lifecycleScope.launchCoroutineWithHandler(dispatcher = Dispatchers.Main) {
            val modelInstance = binding.sceneView.modelLoader.createModelInstance(url)
            modelInstance.let {
                val node = io.github.sceneview.node.ModelNode(
                    modelInstance = it,
                    // tự scale để chiều dài lớn nhất ≈ 1 đơn vị (tuỳ bạn chỉnh)
                    scaleToUnits = 0.5f
                )
                binding.sceneView.addChildNode(node)
            }

        }

    }
}