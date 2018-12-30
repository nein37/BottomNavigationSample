package com.github.nein37.bottomnavigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 渡されたタブごとのタイトルを表示
        title.text = arguments!!.getString("title")

        // 初回のみ初期表示Frgmentを設定
        if (childFragmentManager.findFragmentById(R.id.tab_contents) == null) {
            childFragmentManager.beginTransaction().apply {
                val firstFragment = ContentsFragment()
                replace(R.id.tab_contents, firstFragment)
            }.commit()
        }
    }
}
