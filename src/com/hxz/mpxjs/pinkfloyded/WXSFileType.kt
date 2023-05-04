package com.hxz.mpxjs.pinkfloyded


import com.intellij.lang.javascript.JavaScriptSupportLoader
import com.intellij.openapi.fileTypes.LanguageFileType
import com.hxz.mpxjs.VuejsIcons
import javax.swing.Icon

open class WXSFileType : LanguageFileType(JavaScriptSupportLoader.JAVASCRIPT_1_8) {
    companion object {
        @JvmField
        val INSTANCE = WXSFileType()
    }

    override fun getIcon(): Icon? {
        return VuejsIcons.WXS
    }

    override fun getName(): String {
        return "MPXWXS"
    }

    override fun getDefaultExtension(): String {
        return "wxs"
    }

    override fun getDescription(): String {
        return "Mpx of wxs"
    }

    override fun getDisplayName(): String {
        return "Mpx wxs"
    }
}
