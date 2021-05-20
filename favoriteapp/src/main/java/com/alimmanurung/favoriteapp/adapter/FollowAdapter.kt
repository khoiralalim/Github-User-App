package  com.alimmanurung.favoriteapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alimmanurung.favoriteapp.view.fragment.FollowFragment
import com.alimmanurung.favoriteapp.view.fragment.FollowFragment.Companion.OBJECT

class FollowAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        val followFragment = FollowFragment()
        followFragment.arguments = Bundle().apply {
            putStringArray(OBJECT, arrayOf("$position", username))
        }
        return followFragment
    }


}
