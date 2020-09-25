package ly.test.pagingtest.user

import ly.test.pagingtest.arch.Intent

sealed class UserIntent() : Intent {
    object AddUser : UserIntent()
    object GetUser : UserIntent()
}