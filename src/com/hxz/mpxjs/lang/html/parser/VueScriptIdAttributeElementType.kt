// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.hxz.mpxjs.lang.html.parser

import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.xml.stub.XmlAttributeStubImpl
import com.intellij.psi.impl.source.xml.stub.XmlStubBasedAttributeElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.xml.XmlAttribute
import org.jetbrains.annotations.NonNls
import com.hxz.mpxjs.index.VueIdIndex
import com.hxz.mpxjs.lang.html.VueLanguage

class VueScriptIdAttributeElementType : XmlStubBasedAttributeElementType("SCRIPT_ID_ATTRIBUTE", VueLanguage.INSTANCE) {
  override fun indexStub(stub: XmlAttributeStubImpl, sink: IndexSink) {
    stub.value?.let { sink.occurrence(VueIdIndex.KEY, it) }
  }

  override fun shouldCreateStub(node: ASTNode?): Boolean {
    return (node?.psi as? XmlAttribute)?.parent
      ?.getAttribute("type")?.value == "text/x-template"
  }
}
