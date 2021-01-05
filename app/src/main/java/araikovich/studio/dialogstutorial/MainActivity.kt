package araikovich.studio.dialogstutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import araikovich.studio.dialogstutorial.databinding.ActivityMainBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_example.*
import kotlinx.android.synthetic.main.bottom_sheet_example.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(
            binding.root.bottomSheetParentContainer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomSheet()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnAlertDialog.setOnClickListener {
            showAlertDialog()
        }
        binding.btnDialogFragment.setOnClickListener {
            showDialogFragment()
        }
        binding.btnBottomSheetDialog.setOnClickListener {
            showBottomSheet()
        }
        binding.btnBottomSheetFragment.setOnClickListener {
            showBottomSheetFragment()
        }
        binding.btnMaterialDialog.setOnClickListener {
            showMaterialDialog()
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Alert Demo")
            .setMessage("This is alert dialog message")
            .setIcon(R.drawable.ic_launcher_foreground)
            .setItems(arrayOf("1", "2", "3")) { dialog, which ->
                Toast.makeText(this, "Item clicked", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Positive Btn") { dialog, which ->
                Toast.makeText(this, "Positive clicked", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Negative btn") { dialog, which ->
                Toast.makeText(this, "Negative clicked", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun showDialogFragment() {
        val dialog = DialogFragmentExample()
        dialog.setupResultCallBack { text ->
            binding.btnDialogFragment.text = text
        }
        supportFragmentManager.beginTransaction()
            .add(dialog, "TAG")
            .commitAllowingStateLoss()
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
    }

    private fun showBottomSheetFragment() {
        supportFragmentManager.beginTransaction().add(BottomSheetDialogFragmentExample(), "TAG")
            .commitNowAllowingStateLoss()
    }

    private fun showMaterialDialog() {
        MaterialDialog(this).show {
            title(text = "Title")
            message(text = "Some text")
            positiveButton(text = "Agree") { dialog ->
                Toast.makeText(this@MainActivity, "Positive clicked", Toast.LENGTH_LONG).show()
            }
            negativeButton(text = "Disagree") { dialog ->
                Toast.makeText(this@MainActivity, "Positive clicked", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("LOG_TAG", "slideOffset -- $slideOffset")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.d("LOG_TAG", "new state -- $newState")
            }
        })
    }
}