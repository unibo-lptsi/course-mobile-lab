package com.example.android.unscramble.ui.game

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.android.unscramble.R

class FinalScoreDialogFragment(val viewmodel: GameViewModel,
                               private val negativeLogic: DialogInterface.OnClickListener,
                               private val positiveLogic: DialogInterface.OnClickListener
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.score_dialog_title))
                .setMessage(getString(R.string.score_msg, viewmodel.score.value, viewmodel.MAX_NO_OF_WORDS))
                .setCancelable(false) // cannot press "back" button
                .setNegativeButton(getString(R.string.exit), negativeLogic)
                .setPositiveButton(getString(R.string.try_again), positiveLogic)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}