package com.hxz.mpxjs.pinkfloyded

import com.intellij.json.psi.JsonProperty
import com.intellij.lang.HelpID
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement

class ComponentRegistrationFindUsageProvider : FindUsagesProvider {

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return (element as? JsonProperty)?.name ?: ""
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return this.getNodeText(element, false)
    }

    override fun getType(element: PsiElement): String {
        return if (element is JsonProperty) "component registration" else ""
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return HelpID.FIND_OTHER_USAGES
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is JsonProperty
    }
}
