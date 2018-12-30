package com.github.nein37.bottomnavigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_contents.*

class ContentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentPage = arguments?.getInt("key") ?: 1
        message.text = currentPage.toString()
        button.setOnClickListener {

            // Fragment.requireFragmentManager() は自分自身の遷移に利用された FragmentManagerを返す
            // (ここでは PrimarynavigationFragment の childFragmentmanager )
            requireFragmentManager().beginTransaction().apply {
                val nextFragment = ContentsFragment().apply {
                    arguments = Bundle().apply {
                        // 現在のページ数に +1 して次のページへ
                        putInt("key", currentPage + 1)
                    }
                    addToBackStack(null)
                }
                // Fragment.getId() は自分自身の遷移に利用された id
                // (ここでは R.id.tab_contents)
                replace(id, nextFragment)
            }.commit()
        }
    }
}
