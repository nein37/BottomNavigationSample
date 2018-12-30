package com.github.nein37.bottomnavigationsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private fun changeBottomNav(tag: String) {

        // 各タブごとの TabFragment を検索
        val homeFragment = supportFragmentManager.findFragmentByTag("home");
        val dashboardFragment = supportFragmentManager.findFragmentByTag("dashboard");
        val notificationFragment = supportFragmentManager.findFragmentByTag("notification");

        // 遷移先の TabFragment を設定
        val targetFragment = when (tag) {
            "home" -> homeFragment
            "dashboard" -> dashboardFragment
            "notification" -> notificationFragment
            else -> throw IllegalArgumentException()
        }

        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.primaryNavigationFragment?.let {
                // すでに primaryNavigationFragment が set されている場合は detach
                // detach された Fragment の View は破棄されるが childFragmentManager の backStack などは保持される
                detach(it)
            }

            if (targetFragment == null) {
                // 初回のみタブごとの TabFragment を作成して add する
                val primaryNavigationFragment = TabFragment().apply {
                    arguments = Bundle().apply { putString("title", tag) }
                }
                add(R.id.contents, primaryNavigationFragment, tag)

                // TabFragment に設定することで popBackStack() する際に TabFragment の childFragmentManager の backStack があればそれを pop するようになる
                setPrimaryNavigationFragment(primaryNavigationFragment)
            } else {
                // すでに TabFragment が存在する場合は attach
                attach(targetFragment)
                // TabFragment 再設定
                setPrimaryNavigationFragment(targetFragment)
            }
        }.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    changeBottomNav("home")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    changeBottomNav("dashboard")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    changeBottomNav("notification")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        // 初期タブ選択
        if (!navigation.isSelected) {
            navigation.selectedItemId = R.id.navigation_home
        }
    }

}
