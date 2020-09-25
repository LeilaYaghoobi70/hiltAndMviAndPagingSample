package ly.test.pagingtest.arch

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel


interface Model<s : State, I : Intent> {

    val intents : Channel<I>
    val userState : LiveData<s>
}