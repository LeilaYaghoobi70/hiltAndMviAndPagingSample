package ly.test.pagingtest.arch

interface View <s : State>{
    fun render(state : s)
}