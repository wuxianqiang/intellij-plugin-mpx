// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.hxz.mpxjs.lang.html.parser;

import com.intellij.psi.impl.source.xml.stub.XmlStubBasedAttributeElementType;
import com.intellij.psi.impl.source.xml.stub.XmlStubBasedElementType;
import com.hxz.mpxjs.lang.html.VueLanguage;
import com.hxz.mpxjs.lang.html.psi.impl.VueRefAttributeImpl;
import com.hxz.mpxjs.lang.html.psi.impl.VueRefAttributeStubImpl;

public interface VueStubElementTypes {

  int VERSION = 8;

  VueStubBasedTagElementType STUBBED_TAG = new VueStubBasedTagElementType("STUBBED_TAG");

  VueTemplateTagElementType TEMPLATE_TAG = new VueTemplateTagElementType();

  XmlStubBasedAttributeElementType STUBBED_ATTRIBUTE = new XmlStubBasedAttributeElementType("STUBBED_ATTRIBUTE", VueLanguage.Companion.getINSTANCE());

  VueScriptIdAttributeElementType SCRIPT_ID_ATTRIBUTE = new VueScriptIdAttributeElementType();

  VueSrcAttributeElementType SRC_ATTRIBUTE = new VueSrcAttributeElementType();

  XmlStubBasedElementType<VueRefAttributeStubImpl, VueRefAttributeImpl> REF_ATTRIBUTE = new VueRefAttributeElementType();

}
