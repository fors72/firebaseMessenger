package com.firebase.messenger.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.firebase.messenger.R
import com.firebase.messenger.presentation.view.fragment.BaseFragment
import com.firebase.messenger.presentation.view.fragment.DialogFragment
import com.firebase.messenger.presentation.view.fragment.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val USER_FRAGMENT = 1
    val DIALOG_FRAGMENT = 2
    val PROFILE_FRAGMENT = 3
    var userListFragmentProvider:kotlin.Lazy<UserListFragment> = lazy { UserListFragment() }
    var dialogFragmentProvider:kotlin.Lazy<DialogFragment> = lazy { DialogFragment() }
//    @Inject lateinit var FragmentProvider:Lazy<>

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_users -> {
                initFragment(USER_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_messages -> {
                initFragment(DIALOG_FRAGMENT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initFragment(DIALOG_FRAGMENT)
        navigation.selectedItemId = R.id.navigation_messages
    }


    private fun initFragment(fragmentId:Int){
        fun startFragment(fragment: BaseFragment){
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
        startFragment(when(fragmentId){
            USER_FRAGMENT -> userListFragmentProvider.value
            DIALOG_FRAGMENT -> dialogFragmentProvider.value
            else -> throw IllegalAccessException("Unknown fragment")
        })
    }

}
