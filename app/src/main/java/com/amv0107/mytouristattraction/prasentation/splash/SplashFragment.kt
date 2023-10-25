package com.amv0107.mytouristattraction.prasentation.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amv0107.mytouristattraction.databinding.FragmentSplashBinding
import com.amv0107.mytouristattraction.prasentation.OnAuthenticationLaunch
import com.amv0107.mytouristattraction.prasentation.SharedViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("SplashFragment == null")

    private val animatorSet = AnimatorSet()
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startAnimation(binding.image)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        //Configure sign-in to request the user's ID, email address, and basic under details.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("151342003554-hfs7lsdmqadspvdlsbas0r7tjjrvqson.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val activity = requireActivity() as OnAuthenticationLaunch
        if (account == null) {
            showSignInButton()
        } else {
            viewModel.localCity.observe(viewLifecycleOwner) {
                viewModel.getAllLocations(it)
            }
            viewModel.locations.observe(viewLifecycleOwner) {
                activity.showListFragment()
            }
        }

        binding.signInButton.setOnClickListener {
            activity.launch(googleSignInClient.signInIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startAnimation(image: ImageView) {
        val scaleXAnimation = ObjectAnimator.ofFloat(image, View.SCALE_X, 0.5f, 1f)
        scaleXAnimation.repeatMode = ObjectAnimator.REVERSE
        scaleXAnimation.repeatCount = ObjectAnimator.INFINITE
        val scaleYAnimation = ObjectAnimator.ofFloat(image, View.SCALE_Y, 0.5f, 1f)
        scaleYAnimation.repeatMode = ObjectAnimator.REVERSE
        scaleYAnimation.repeatCount = ObjectAnimator.INFINITE
        animatorSet.playTogether(scaleXAnimation, scaleYAnimation)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    private fun showSignInButton() {
        binding.signInButton.visibility = View.VISIBLE
        animatorSet.cancel()
    }
}