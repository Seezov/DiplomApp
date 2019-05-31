package com.example.workloadtracker.activities

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.workloadtracker.R
import com.example.workloadtracker.SPCache
import com.example.workloadtracker.api.ApiClient
import com.example.workloadtracker.database.AppDatabase
import com.example.workloadtracker.database.DBUtils
import com.example.workloadtracker.fragments.PlanFragment
import com.example.workloadtracker.fragments.WorkloadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dialog_login.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    lateinit var db: AppDatabase

    lateinit var spCache: SPCache

    lateinit var lecturersAdapter: ArrayAdapter<String>

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.logout) {
            showLoginDialog()
            spCache
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoginDialog() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_login, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false)
        //show dialog
        val mAlertDialog = mBuilder.show()

        inflateSpinner(mDialogView.spinnerLecturer, db.lecturerDao().getAll().map { it.name }.toMutableList())

        mDialogView.btnLogin.setOnClickListener {
            mAlertDialog.dismiss()
            spCache.currentLecturer = db.lecturerDao().getByName(mDialogView.spinnerLecturer.selectedItem.toString())
            bottomNavigation.selectedItemId = R.id.navigation_plan
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        spCache = SPCache()
        db = DBUtils().initDb(this)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.101:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiClient = retrofit.create(ApiClient::class.java)

        bottomNavigation = findViewById(R.id.navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_plan -> {
                    toolbar.title = "План"
                    val planFragment = PlanFragment.newInstance(db, spCache)
                    openFragment(planFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_workload -> {
                    toolbar.title = "Навантаження"
                    val workloadFragment = WorkloadFragment.newInstance(apiClient, db, spCache)
                    openFragment(workloadFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        Thread(Runnable {
            // Do network action in this function

            try {


                val responseDisciplines = apiClient.getDisciplines().execute()
                if (responseDisciplines.isSuccessful) {
                    db.disciplineDao().addAll(
                        responseDisciplines.body()!!
                    )
                }

                val responseGroupCodes = apiClient.getGroupCodes().execute()
                if (responseGroupCodes.isSuccessful) {
                    db.groupCodeDao().addAll(
                        responseGroupCodes.body()!!
                    )
                }

                val responseEducationForms = apiClient.getEducationForms().execute()
                if (responseEducationForms.isSuccessful) {
                    db.educationFormDao().addAll(
                        responseEducationForms.body()!!
                    )
                }

                val responseLessonTypes = apiClient.getLessonTypes().execute()
                if (responseLessonTypes.isSuccessful) {
                    db.lessonTypeDao().addAll(
                        responseLessonTypes.body()!!
                    )
                }

                val responseLecturers = apiClient.getLecturers().execute()
                if (responseLecturers.isSuccessful) {
                    db.lecturerDao().addAll(
                        responseLecturers.body()!!
                    )
                    runOnUiThread {
                        lecturersAdapter.clear()
                        lecturersAdapter.addAll(db.lecturerDao().getAll().map { it.name })
                        lecturersAdapter.notifyDataSetChanged()
                    }
                }

                val responseRates = apiClient.getRates().execute()
                if (responseRates.isSuccessful) {
                    db.rateDao().addAll(
                        responseRates.body()!!
                    )
                }

                val responseWorkloads = apiClient.getWorkloads().execute()
                if (responseWorkloads.isSuccessful) {
                    db.workloadDao().addAll(
                        responseWorkloads.body()!!
                    )
                }

                val responsePlans = apiClient.getPlans().execute()
                if (responsePlans.isSuccessful) {
                    db.planDao().addAll(
                        responsePlans.body()!!
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()

        showLoginDialog()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun inflateSpinner(spinner: Spinner, objects: MutableList<String>) {
        // Create an ArrayAdapter using a simple spinner layout and languages array
        lecturersAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, objects)
        // Set layout to use when the list of choices appear
        lecturersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = lecturersAdapter
    }
}
