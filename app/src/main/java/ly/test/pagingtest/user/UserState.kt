package ly.test.pagingtest.user

import ly.test.pagingtest.arch.State
import ly.test.pagingtest.model.User
import java.net.UnknownServiceException

sealed class UserState() : State{
    object IsAddUser : UserState()
    data class AddUser(val user: User) : UserState()
    data class GetUser(val users: List<User>) : UserState()
}