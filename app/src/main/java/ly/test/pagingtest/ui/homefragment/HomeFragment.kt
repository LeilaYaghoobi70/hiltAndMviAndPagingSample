package ly.test.pagingtest.ui.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ly.test.pagingtest.databinding.FragmentLoginBinding
import ly.test.pagingtest.model.User
import ly.test.pagingtest.user.UserIntent

import ly.test.pagingtest.user.UserState

@AndroidEntryPoint
class HomeFragment : Fragment(), ly.test.pagingtest.arch.View<UserState> {

    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null

    private lateinit var adapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = UserAdapter(viewModel.users)
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            viewmodel = this@HomeFragment.viewModel

            add.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.intents.send(UserIntent.AddUser)
                }
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.userState.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    override fun render(state: UserState) {
        when (state) {
            UserState.IsAddUser -> {
                binding?.nameTextView?.setText("")
                binding?.familyTextView?.setText("")
                lifecycleScope.launch { viewModel.intents.send(UserIntent.GetUser) }
            }
            is UserState.GetUser -> {
                adapter.submit(state.users as ArrayList<User>)
            }

        }
    }

}