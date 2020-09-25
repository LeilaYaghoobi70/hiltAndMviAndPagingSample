package ly.test.pagingtest.ui.homefragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import ly.test.pagingtest.arch.Model
import ly.test.pagingtest.model.Repositroy
import ly.test.pagingtest.model.User
import ly.test.pagingtest.user.UserIntent
import ly.test.pagingtest.user.UserState

class HomeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel(), Model<UserState, UserIntent> {

    private val _state = MutableLiveData<UserState>()
    private val state: LiveData<UserState> = _state

    val users: ArrayList<User> = ArrayList()

    val name: MutableLiveData<String> = MutableLiveData()
    val family: MutableLiveData<String> = MutableLiveData()

    override val intents: Channel<UserIntent> = Channel(Channel.UNLIMITED)
    override val userState: LiveData<UserState> = state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { userIntent ->
                when (userIntent) {
                    UserIntent.AddUser -> addUser()
                    UserIntent.GetUser -> fetchDate()
                }
            }

        }

    }

    private fun fetchDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(UserState.GetUser(Repositroy.getUser()))
        }
    }

    private fun addUser() {
        val user = User(name.value!!, family.value!!)
        viewModelScope.launch(Dispatchers.IO) {
            Repositroy.addUser(user)
            _state.postValue(UserState.IsAddUser)
        }
    }

}