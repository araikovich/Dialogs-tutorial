package araikovich.studio.dialogstutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import araikovich.studio.dialogstutorial.databinding.BottomSheetDialgFragmentExampleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFragmentExample : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialgFragmentExampleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetDialgFragmentExampleBinding.inflate(inflater, container, false)
        return binding.root
    }
}