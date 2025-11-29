
package com.example.mealna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class OnboardingFragment : Fragment() {

    private var layoutResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutResId = it.getInt(LAYOUT_RES_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bail out if the layout ID is invalid to prevent a crash.
        if (layoutResId == 0) {
            return null
        }

        val view = inflater.inflate(layoutResId, container, false)

        // Safely find the button and set the listener only if the layout is correct.
        if (layoutResId == R.layout.onboarding_3) {
            view.findViewById<Button>(R.id.btn_get_started)?.setOnClickListener {
                (activity as? OnboardingActivity)?.navigateToHome()
            }
        }

        return view
    }

    companion object {
        private const val LAYOUT_RES_ID = "layout_res_id"

        @JvmStatic
        fun newInstance(layoutResId: Int) = OnboardingFragment().apply {
            arguments = Bundle().apply {
                putInt(LAYOUT_RES_ID, layoutResId)
            }
        }
    }
}
