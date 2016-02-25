package pl.elpassion.cloudtimer.group

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.larswerkman.holocolorpicker.ColorPicker
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.domain.User
import java.util.*

class NewGroupActivity : CloudTimerActivity() {

    companion object {
        private val timerUUIDKey = "TIMERUUID"
        fun start(context: Context, sharedTimerUUID: String) {
            Log.e("ACTIVITY", " New Group Activity created")
            val intent = Intent(context, NewGroupActivity::class.java)
            intent.putExtra(timerUUIDKey, sharedTimerUUID)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    private val newGroupToolbar by lazy { findViewById(R.id.new_group_toolbar) as Toolbar }
    private val timersRecyclerView by lazy { findViewById(R.id.timers_recycler_view) as RecyclerView }
    private val usersRecyclerView by lazy { findViewById(R.id.users_recycler_view) as RecyclerView }
    private val colorPicker by lazy { findViewById(R.id.group_color_picker) as ColorPicker }
    private val colorPickerLayout by lazy { findViewById(R.id.color_picker_layout) as LinearLayout }
    private val colorMenuIcon by lazy { findViewById(R.id.group_colour_settings) }
    private val addUserButton by lazy { findViewById(R.id.add_new_user_button) as FloatingActionButton }
    private val addUserLayout by lazy { findViewById(R.id.enter_emile_layout) as LinearLayout }
    private val emileEditText by lazy { findViewById(R.id.emile_edit_text) as EditText }
    private val colorPickerHiderUP by lazy { findViewById(R.id.color_picker_up_view) }
    private val colorPickerHiderDOWN by lazy { findViewById(R.id.color_picker_down_view) }
    private val addUserEmailButton by lazy { findViewById(R.id.add_user_emile_button) as Button }
    private val randomColor = Group.randomColor()
    private val groupColorIcon = createGroupColorIcon()

    private val timers: MutableList<Timer> = ArrayList()
    private val users: MutableList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_group)
        newGroupToolbar.inflateMenu(R.menu.new_group_menu)
        loadRecyclerViews()
        setUpColorPicker()
        setUpGroupViewListeners()
    }

    private fun loadRecyclerViews() {
        loadAndSetUpTimersRecycleView()
        loadAndSetUpUsersRecyclerView()
    }

    private fun setUpColorPicker() {
        colorMenuIcon.background = groupColorIcon
        colorPicker.showOldCenterColor = false
        colorPicker.color = randomColor
        setUpColorPickerListeners()
    }

    private fun setUpColorPickerListeners() {
        colorPicker.setOnColorChangedListener {
            groupColorIcon.setColor(colorPicker.color)
        }
        colorPickerHiderUP.setOnClickListener {
            backFromLayout(colorPickerLayout)
        }
        colorPickerHiderDOWN.setOnClickListener {
            backFromLayout(colorPickerLayout)
        }
        colorMenuIcon.setOnClickListener {
            if (isViewVisible(colorPickerLayout))
                backFromLayout(colorPickerLayout)
            else
                colorPickerLayout.visibility = VISIBLE
        }
    }

    private fun setUpGroupViewListeners() {
        addUserLayout.setOnClickListener {
            backFromLayout(addUserLayout)
        }
        addUserButton.setOnClickListener {
            addUserLayout.visibility = VISIBLE
        }
        addUserEmailButton.setOnClickListener {
            emailButtonClickAction()
        }
    }

    private fun emailButtonClickAction() {
        val emailString = emileEditText.text.toString()
        val emailsList = (emailString.split("\n"))
        for (email in emailsList) {
            val isUserWithEmailAvailable = users.none { it.email.equals(email) }
            if (isUserWithEmailAvailable)
                users.add(User(email.replace("@*.".toRegex(), ""), email))
        }
        setUpUsersRecyclerView()
        backFromLayout(addUserLayout)
    }

    private fun loadAndSetUpTimersRecycleView() {
        timersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        if (intent.extras != null)
            loadSharedTimerFromDB(intent.extras.getString(timerUUIDKey))
        else loadSharedTimerFromDB("test")
        setUpTimersRecyclerView()
    }

    private fun loadSharedTimerFromDB(timerUUID: String) {
        val dao = TimerDAO.getInstance()
        timers.clear()
        timers.add(dao.findOne(timerUUID))
        timers.add(Timer("", 60000))
        timers.add(Timer("", 70000))
        timers.add(Timer("", 80000))
    }

    private fun setUpTimersRecyclerView() {
        val newAdapter = NewGroupListOfTimersAdapter()
        newAdapter.updateTimers(timers)
        timersRecyclerView.adapter = newAdapter
    }

    private fun loadAndSetUpUsersRecyclerView() {
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        loadUsers()
        setUpUsersRecyclerView()
    }

    private fun loadUsers() {
        //todo Download users from server
        users.add(User("Mietek", "mietek@gmail.com"))
        users.add(User("Ziutek", "ziutek@gmail.com"))
        users.add(User("Andrzej", "andrzej@gmail.com"))
        users.add(User("Mietek", "mietek@gmail.com"))
        users.add(User("Ziutek", "ziutek@gmail.com"))
        users.add(User("Andrzej", "andrzej@gmail.com"))
        users.add(User("Mietek", "mietek@gmail.com"))
        users.add(User("Ziutek", "ziutek@gmail.com"))
        users.add(User("Andrzej", "andrzej@gmail.com"))
        users.add(User("Mietek", "mietek@gmail.com"))
        users.add(User("Ziutek", "ziutek@gmail.com"))
        users.add(User("Andrzej", "andrzej@gmail.com"))
    }

    private fun setUpUsersRecyclerView() {
        val newAdapter = NewGroupListOfUsersAdapter()
        newAdapter.updateUsers(users)
        usersRecyclerView.adapter = newAdapter
    }

    private fun createGroupColorIcon(): GradientDrawable {
        val gd = GradientDrawable()
        gd.setShape(GradientDrawable.RADIAL_GRADIENT)
        gd.setStroke(15, Color.WHITE)
        gd.setColor(randomColor)
        return gd
    }

    private fun backFromLayout(layout: LinearLayout) {
        layout.visibility = GONE
    }

    override fun onBackPressed() {
        if (isViewVisible(addUserLayout))
            backFromLayout(addUserLayout)
        else
            super.onBackPressed()
    }

    private fun isViewVisible(view: LinearLayout) = view.visibility == VISIBLE
}