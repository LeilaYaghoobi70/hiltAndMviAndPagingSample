package ly.test.pagingtest.ui.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ly.test.pagingtest.databinding.ItemUserRecyclerviewBinding
import ly.test.pagingtest.model.User

class UserAdapter constructor(var users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = users.forEach { holder.onBind(it) }

    override fun getItemCount(): Int = users.size

    fun submit(newUsers : ArrayList<User>){
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(UserDiffUtils(users,newUsers))
        users.clear()
        users.addAll(newUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(private val binding: ItemUserRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.familyTextView.text = user.family
            binding.nameTextView.text = user.name
        }
    }

    inner class UserDiffUtils(
        private val oldUsers : ArrayList<User>,
        private val newUsers : ArrayList<User>
    ):DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldUsers.size

        override fun getNewListSize(): Int  = newUsers.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItemPosition == newItemPosition

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = newUsers[newItemPosition] == oldUsers[oldItemPosition]
    }
}