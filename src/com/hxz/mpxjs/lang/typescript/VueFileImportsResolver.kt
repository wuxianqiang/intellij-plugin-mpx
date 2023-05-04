// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.typescript

import com.intellij.lang.javascript.DialectDetector
import com.intellij.lang.typescript.modules.TypeScriptNodeSearchProcessor
import com.intellij.lang.typescript.tsconfig.TypeScriptConfig
import com.intellij.lang.typescript.tsconfig.TypeScriptFileImportsResolver
import com.intellij.lang.typescript.tsconfig.TypeScriptImportResolveContext
import com.intellij.lang.typescript.tsconfig.TypeScriptImportsResolverProvider
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.index.findModule
import com.hxz.mpxjs.lang.html.VueFileType

import com.intellij.javascript.nodejs.NodeModuleDirectorySearchProcessor
import com.intellij.lang.javascript.psi.JSExecutionScope
import com.intellij.lang.javascript.psi.stubs.TypeScriptScriptContentIndex
import com.intellij.lang.typescript.tsconfig.TypeScriptFileImportsResolverImpl
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.Processor

const val VUE_FILE_EXTENSION = ".mpx"
val VUE_DEFAULT_EXTENSIONS_WITH_DOT = arrayOf(VUE_FILE_EXTENSION)
class VueFileImportsResolver(project: Project,
                             resolveContext: TypeScriptImportResolveContext,
                             nodeProcessor: NodeModuleDirectorySearchProcessor) :
  TypeScriptFileImportsResolverImpl(project, resolveContext, VUE_DEFAULT_EXTENSIONS_WITH_DOT, listOf(VueFileType.INSTANCE)) {

  override fun processAllFilesInScope(includeScope: GlobalSearchScope, processor: Processor<in VirtualFile>) {
    if (includeScope == GlobalSearchScope.EMPTY_SCOPE) return
    StubIndex.getInstance().processElements(
      TypeScriptScriptContentIndex.KEY, TypeScriptScriptContentIndex.DEFAULT_INDEX_KEY, project,
      includeScope, null, JSExecutionScope::class.java) {

      ProgressManager.checkCanceled()
      val virtualFile = it.containingFile.virtualFile
      if (virtualFile != null && fileTypes.contains(virtualFile.fileType)) {
        if (!processor.process(virtualFile)) return@processElements false
      }
      return@processElements true
    }
  }
}
