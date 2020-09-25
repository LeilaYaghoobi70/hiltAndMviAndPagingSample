package ly.test.pagingtest.model

object  Repositroy {

    private val users: ArrayList<User> = ArrayList()
    fun addUser(user:User){
        users.add(user)
    }
    fun getUser(): ArrayList<User> = users
}