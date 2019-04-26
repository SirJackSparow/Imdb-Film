package com.example.bima.pariwisatayogyakarte.helper

import android.app.Activity
import android.content.Context
import android.support.v7.widget.DialogTitle
import android.view.View
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder

class BubleHelp {
    companion object {
        fun getSimpleChase(context : Context,desck: String,title: String):BubbleShowCaseBuilder{
            return BubbleShowCaseBuilder(context as Activity)
                .title(title)
                .description(desck)

        }
    }
}


