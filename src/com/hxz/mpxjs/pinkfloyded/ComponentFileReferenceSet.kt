package com.hxz.mpxjs.pinkfloyded

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.application.runReadAction
import com.hxz.mpxjs.lang.html.VueFileType

class ComponentFileReferenceSet(psiElement: PsiElement) : FileReferenceSet(psiElement) {

    override fun createFileReference(range: TextRange, index: Int, text: String): FileReference {
        return ComponentFileReference(this, range, index, text)
    }

}

class ComponentFileReference(
    componentFileReferenceSet: ComponentFileReferenceSet,
    range: TextRange,
    index: Int,
    text: String
) : FileReference(
    componentFileReferenceSet,
    range, index, text
) {

    override fun innerResolveInContext(
        text: String,
        context: PsiFileSystemItem,
        result: MutableCollection<in ResolveResult>,
        caseSensitive: Boolean
    ) {
        super.innerResolveInContext(text, context, result, caseSensitive)
        val isFirst = index == 0
        val miniProgramNpmRootDir = findMiniProgramNpmRootDir(context.project)
        val tryToSearchInNpm = isFirst && !fileReferenceSet.element.text.startsWith(
            "/"
        ) && !fileReferenceSet.element.text.startsWith(
            ".."
        ) && miniProgramNpmRootDir != null

        val directoryFromNpm = if (tryToSearchInNpm) {
            // is first reference

            // search directory in npm root
            miniProgramNpmRootDir!!.findSubdirectory(text)
        } else {
            null
        }
        directoryFromNpm?.let {
            result.add(PsiElementResolveResult(it))
        }


        if (isLast && context is PsiDirectory) {
            result.addAll(findRelateFilesFromDirectory(context, text))
        }

        if (directoryFromNpm != null && isLast) {
            // resolving index files from npm directory
            result.addAll(findRelateFilesFromDirectory(directoryFromNpm, "index"))
        }

    }

    private fun findRelateFilesFromDirectory(
        directoryFromNpm: PsiDirectory, text: String
    ) = directoryFromNpm.files.filter {
        it.virtualFile.nameWithoutExtension == (text.split("?")[0]) && (it.virtualFile.fileType == VueFileType.INSTANCE)
    }.map {
        PsiElementResolveResult(it)
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        // 重命名移除文件名后缀
        val dotIndex = newElementName.lastIndexOf(".")
        return if (dotIndex == -1) {
            super.handleElementRename(newElementName)
        } else {
            super.handleElementRename(
                newElementName.substring(0 until dotIndex)
            )
        }
    }
}


fun findProjectRootDir(project: Project): PsiDirectory? {
    return project.basePath?.let {
        LocalFileSystem.getInstance().findFileByPath(it)
    }?.let {
        runReadAction {
            // 读取文件内容创建文件
            PsiManager.getInstance(project).findDirectory(it)
        }
    }
}

fun findMiniProgramNpmRootDir(project: Project): PsiDirectory? {
    return findProjectRootDir(project)?.let {
        if (it.findFile("package.json") != null && it.findSubdirectory("node_modules") != null) {
            // package.json is existing
            // node_modules is existing
            it.findSubdirectory("node_modules")
        } else {
            null
        }
    }
}
