package com.example.petproject2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

private const val NUM_PAGES = 6

class PetPageActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentlist: Array<PetScenarioSliderFragment>
    var petId: Int = 1

    private val scenarios = PetPageScenarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        val petNameView = findViewById<TextView>(R.id.petNameView)
        val avatarImageView = findViewById<ImageView>(R.id.petAvatarView)

        val packageName = this.packageName
        val oldSavedIntent = getIntent()
        petId = oldSavedIntent.getIntExtra("PetId", -1)

        //scenarios.onStart(supportFragmentManager, petId)
        avatarImageView.setImageResource(this.getResources().getIdentifier(oldSavedIntent.getStringExtra("Avatar"), "drawable", packageName))
        petNameView.setText(oldSavedIntent.getStringExtra("Name"))


        viewPager = findViewById(R.id.pager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter/*
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                fragmentlist[position].showContent(petId)
            }
        })
        fragmentlist = arrayOf(DocumentsFragment.newInstance(petId), MedicalFragment.newInstance(petId), NotificationFragment.newInstance(petId), HabitFragment.newInstance(petId), RatioFragment.newInstance(petId), MeasurementsFragment.newInstance(petId))*/
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()

    }
    override fun onResume() {
        super.onResume()

        //scenarios.onResume()
        /*fragmentlist.forEach { fragment ->
            fragment.showContent(petId)
        }*/
    }



    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            lateinit var fragment: Fragment
            when(position) {
                0 -> fragment = DocumentsFragment.newInstance(petId)
                1 -> fragment = MedicalFragment.newInstance(petId)
                2 -> fragment = NotificationFragment.newInstance(petId)
                3 -> fragment = HabitFragment.newInstance(petId)
                4 -> fragment = RatioFragment.newInstance(petId)
                5 -> fragment = MeasurementsFragment.newInstance(petId)
                else -> fragment = DocumentsFragment.newInstance(petId)
            }
            return fragment
        }
    }
}
