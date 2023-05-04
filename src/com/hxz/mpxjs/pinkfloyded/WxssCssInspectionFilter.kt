package com.hxz.mpxjs.pinkfloyded

import com.hxz.mpxjs.context.isVueContext
import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.psi.PsiElement

class WxssCssInspectionFilter : InspectionSuppressor {

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        return SuppressQuickFix.EMPTY_ARRAY
    }

    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        return toolId == "CssInvalidPropertyValue" && element.text.contains("rpx") && isVueContext(
            element
        )
    }
}

