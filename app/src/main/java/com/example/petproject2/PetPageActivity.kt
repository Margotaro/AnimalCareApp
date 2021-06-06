package com.example.petproject2

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 6

class PetPageActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentlist: Array<PetScenarioSliderFragment>
    var petId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_page)
        val petNameView = findViewById<TextView>(R.id.petNameView)
        val avatarImageView = findViewById<ImageView>(R.id.petAvatarView)

        val packageName = this.packageName
        val oldSavedIntent = getIntent()
        petId = oldSavedIntent.getIntExtra("PetId", -1)

        val avatarUri = Uri.parse(oldSavedIntent.getStringExtra("Avatar"))
        val avatarPlaceholderId = this.getResources().getIdentifier("pet_placeholder1", "drawable", packageName)
        var selectedImage = BitmapFactory.decodeResource(this.getResources(), avatarPlaceholderId)
        if(oldSavedIntent.getStringExtra("Avatar") != null) {
            val avatarUri = Uri.parse(oldSavedIntent.getStringExtra("Avatar"))
            this.contentResolver.openInputStream(avatarUri)?.let { stream ->
                selectedImage = BitmapFactory.decodeStream(stream)
            }
        }
        avatarImageView.setImageBitmap(selectedImage)
        petNameView.setText(oldSavedIntent.getStringExtra("Name"))


        viewPager = findViewById(R.id.pager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            setTabIcon(position, tab)
        }.attach()
        tabLayout.tabIconTint = null
    }

    private fun setTabIcon(position: Int, tab: TabLayout.Tab) {
        if(position == 0)
        {
            val id = resources.getIdentifier("slider_icon_document", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
        if(position == 1)
        {
            val id = resources.getIdentifier("slider_icon_medical_pills", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
        if(position == 2)
        {
            val id = resources.getIdentifier("slider_icon_notification", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
        if(position == 3)
        {
            val id = resources.getIdentifier("slider_icon_routine", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
        if(position == 4)
        {
            val id = resources.getIdentifier("slider_icon_diet", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
        if(position == 5)
        {
            val id = resources.getIdentifier("slider_icon_measurement", "drawable", this.packageName)
            tab.setIcon(id)
            return
        }
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
