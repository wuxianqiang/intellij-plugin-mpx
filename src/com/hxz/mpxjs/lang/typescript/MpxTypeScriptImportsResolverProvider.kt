// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.typescript

import com.intellij.lang.javascript.DialectDetector
import com.intellij.lang.typescript.modules.TypeScriptNodeReference
import com.intellij.lang.typescript.tsconfig.TypeScriptConfig
import com.intellij.lang.typescript.tsconfig.TypeScriptFileImportsResolver
import com.intellij.lang.typescript.tsconfig.TypeScriptImportResolveContext
import com.intellij.lang.typescript.tsconfig.TypeScriptImportsResolverProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.hxz.mpxjs.context.isVueContext
import com.hxz.mpxjs.index.findModule
import com.hxz.mpxjs.lang.html.VueFileType

const val vueExtension = ".mpx"
val defaultExtensionsWithDot = arrayOf(vueExtension)

class VueTypeScriptImportsResolverProvider : TypeScriptImportsResolverProvider {
  override fun isDynamicFile(project: Project, file: VirtualFile): Boolean {
    if (file.fileType != VueFileType.INSTANCE) return false

    val psiFile = PsiManager.getInstance(project).findFile(file) ?: return false
    val module = findModule(psiFile)

    return module != null && DialectDetector.isTypeScript(module)
  }

  override fun useExplicitExtension(extensionWithDot: String): Boolean = extensionWithDot == vueExtension
  override fun getExtensions(): Array<String> = defaultExtensionsWithDot

  override fun contributeResolver(project: Project, config: TypeScriptConfig): TypeScriptFileImportsResolver? {
    return VueFileImportsResolver(project, config.resolveContext, TypeScriptNodeReference.TS_PROCESSOR)
  }

  override fun contributeResolver(project: Project,
                                  context: TypeScriptImportResolveContext,
                                  contextFile: VirtualFile): TypeScriptFileImportsResolver? {
    if (!isVueContext(contextFile, project)) return null

    return VueFileImportsResolver(project, context, TypeScriptNodeReference.TS_PROCESSOR)
  }
}
