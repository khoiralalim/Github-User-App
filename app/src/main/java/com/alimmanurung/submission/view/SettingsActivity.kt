package com.alimmanurung.submission.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alimmanurung.submission.R
import com.alimmanurung.submission.service.Reminder
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        reminder = Reminder()
        btn_alarmon.setOnClickListener(this)
        btn_alarmoff.setOnClickListener(this)
        btn_alarmtest.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_alarmon -> reminder.alarmRepeat(this)
            R.id.btn_alarmoff -> reminder.alarmCancel(this)
            R.id.btn_alarmtest -> reminder.alarmNotif(this)
        }
    }
}