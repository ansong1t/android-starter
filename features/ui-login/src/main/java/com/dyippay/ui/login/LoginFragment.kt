package com.dyippay.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appetiser.ui.login.R
import com.appetiser.ui.login.databinding.FragmentLoginBinding
import com.dyippay.common.FragmentWithBinding
import com.dyippay.util.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class LoginFragment :
    FragmentWithBinding<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(
        binding: FragmentLoginBinding,
        savedInstanceState: Bundle?
    ) {

        binding.txtDontHaveAnAccount.applyInsetter {
            type(navigationBars = true) {
                margin(bottom = true)
            }
        }
        binding.btnSignIn.setOnClickListener {
            viewModel.login()
        }
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
        binding.state = state

        state.viewEvent?.getContentIfNotHandled()?.let {
            when (it) {
                LoginViewEvent.ProceedToHome -> findNavController()
                    .navigate(R.id.action_global_to_home_graph)

                is LoginViewEvent.Error -> requireContext().showAlertDialog(
                    title = "Error",
                    body = it.message
                )
            }
        }
    }
}
