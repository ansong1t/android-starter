package com.appetiser.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.appetiser.ui.login.databinding.FragmentLoginBinding
import com.dyippay.common.FragmentWithBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    FragmentWithBinding<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        binding: FragmentLoginBinding,
        savedInstanceState: Bundle?
    ) {
        viewModel.liveData.observe(viewLifecycleOwner, ::render)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, container, false)

    private fun render(state: LoginViewState) {
        val binding = requireBinding()
    }
}
