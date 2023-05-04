package com.hxz.mpxjs.pinkfloyded

import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType

class WechatMiniProgramJsonSchemaProviderFactory : JsonSchemaProviderFactory {
    override fun getProviders(project: Project): MutableList<JsonSchemaFileProvider> {
        return mutableListOf(PageJsonSchemaFileProvider(project))
    }
}

fun isAppJsonFile(project: Project, virtualFile: VirtualFile): Boolean {
    return virtualFile.name == "app.mpx"
}

fun isPageJsonFile(project: Project, virtualFile: VirtualFile): Boolean {
    return runReadAction {
        val psiFile = PsiManager.getInstance(project).findFile(virtualFile)
        virtualFile.extension == "mpx"
    }
}

fun isRootFile(project: Project, virtualFile: VirtualFile): Boolean {
    val contentRoot = ProjectFileIndex.getInstance(
        project
    ).getContentRootForFile(virtualFile)
    // 在根目录下且名称为 app.json
    return virtualFile.parent == contentRoot
}

private class AppJsonSchemaFileProvider(private val project: Project) : JsonSchemaFileProvider {

    override fun getName(): String {
        return "app.mpx"
    }

    override fun isAvailable(virtualFile: VirtualFile): Boolean {
        return isAppJsonFile(project, virtualFile)
    }

    override fun getSchemaFile(): VirtualFile? {
        return JsonSchemaProviderFactory.getResourceFile(
            this.javaClass,
            "/jsonSchemas/app.json"
        )
    }

    override fun getSchemaType(): SchemaType {
        return SchemaType.schema
    }

}

private class PageJsonSchemaFileProvider(private val project: Project) : JsonSchemaFileProvider {
    override fun getName(): String {
        return "page.js"
    }

    override fun isAvailable(virtualFile: VirtualFile): Boolean {
        return isPageJsonFile(project, virtualFile)
    }

    override fun getSchemaFile(): VirtualFile? {
        return JsonSchemaProviderFactory.getResourceFile(
            this.javaClass,
            "/jsonSchemas/page.json"
        )
    }

    override fun getSchemaType(): SchemaType {
        return SchemaType.schema
    }
}
