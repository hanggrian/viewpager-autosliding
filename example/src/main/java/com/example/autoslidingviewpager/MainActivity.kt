package com.example.autoslidingviewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hendraanggrian.bundler.BindExtra
import com.hendraanggrian.bundler.Bundler
import com.hendraanggrian.text.SpannableText
import com.hendraanggrian.text.SpannedArg
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main3.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spannable = SpannableText.format("It stops sliding once touched. But you can %s.", SpannedArg("start it again", object : ClickableSpan() {
            override fun onClick(widget: View?) {
                group.start()
            }
        }))

        group.viewPager.adapter = Adapter(supportFragmentManager, spannable)
        indicator.setViewPager(group.viewPager)
        group.start()
    }

    class Adapter(fm: FragmentManager, val spannable: CharSequence) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return Fragment1()
                1 -> return Fragment2()
                else -> {
                    val fragment = Fragment3()
                    fragment.arguments = Bundler.wrapExtras(Fragment3::class.java, spannable)
                    return fragment
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }
    }

    class Fragment1 : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_main1, container, false)
        }
    }

    class Fragment2 : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_main2, container, false)
        }
    }

    class Fragment3 : Fragment() {
        @BindExtra lateinit var spannable: CharSequence

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_main3, container, false)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            Bundler.bindExtras(this)
            textView.text = spannable
            textView.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}