package araikovich.studio.dialogstutorial

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.DialogFragment
import araikovich.studio.dialogstutorial.databinding.DialogFragmentExampleBinding

class DialogFragmentExample : DialogFragment() {

    companion object {

        private const val DIALOG_SHOW_ANIM_ALPHA_DURATION = 300L
        private const val DIALOG_SHOW_SCALE_DURATION = 150L

        private const val DIALOG_HIDE_ALPHA_DURATION = 250L
        private const val DIALOG_HIDE_SCALE_FIRST_DURATION = 190L
        private const val DIALOG_HIDE_SCALE_SECOND_DURATION = 60L
    }

    private lateinit var binding: DialogFragmentExampleBinding
    private var resultCallBack: ((String) -> (Unit))? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentExampleBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.tvDescription.setOnClickListener {
            showCloseAnimation()
        }
        binding.btnResult.setOnClickListener {
            resultCallBack?.invoke(binding.tvDescription.text.toString())
        }
    }

    fun setupResultCallBack(resultCallBack: ((String) -> (Unit))) {
        this.resultCallBack = resultCallBack
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showEnterAnimation()
    }

    private fun showEnterAnimation() {
        val set = AnimatorSet()
        val alphaAnim = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.alpha = it.animatedValue as Float
            }
            duration = DIALOG_SHOW_ANIM_ALPHA_DURATION
        }
        val scaleAnimSecond = ValueAnimator.ofFloat(1.02f, 1f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.scaleX = it.animatedValue as Float
                dialog?.window?.decorView?.scaleY = it.animatedValue as Float
            }
            duration = DIALOG_SHOW_SCALE_DURATION
        }
        val scaleAnimFirst = ValueAnimator.ofFloat(0.95f, 1.02f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.scaleX = it.animatedValue as Float
                dialog?.window?.decorView?.scaleY = it.animatedValue as Float
            }
            duration = DIALOG_SHOW_SCALE_DURATION
            doOnEnd {
                scaleAnimSecond.start()
            }
        }
        set.playTogether(alphaAnim, scaleAnimFirst)
        set.start()
    }

    private fun showCloseAnimation() {
        val set = AnimatorSet()
        val alphaAnim = ValueAnimator.ofFloat(1f, 0.2f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.alpha = it.animatedValue as Float
            }
            duration = DIALOG_HIDE_ALPHA_DURATION
        }
        val scaleAnimSecond = ValueAnimator.ofFloat(1f, 1.1f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.scale(it.animatedValue as Float)
            }
            duration = DIALOG_HIDE_SCALE_FIRST_DURATION
            doOnEnd {
                dismissAllowingStateLoss()
            }
        }
        val scaleAnimFirst = ValueAnimator.ofFloat(1f, 0.98f).apply {
            addUpdateListener {
                dialog?.window?.decorView?.scale(it.animatedValue as Float)
            }
            duration = DIALOG_HIDE_SCALE_SECOND_DURATION
            doOnEnd {
                scaleAnimSecond.start()
            }
        }
        set.playTogether(alphaAnim, scaleAnimFirst)
        set.start()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}

fun View.scale(scale: Float) {
    scaleX = scale
    scaleY = scale
}