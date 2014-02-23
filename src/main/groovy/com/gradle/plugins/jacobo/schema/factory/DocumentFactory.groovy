package com.gradle.plugins.jacobo.schema.factory

import com.gradle.plugins.jacobo.schema.document.BaseSchemaDocument

interface DocumentFactory {
  public BaseSchemaDocument createDocument(File document)
}