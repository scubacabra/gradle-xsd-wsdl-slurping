package org.gradle.jacobo.schema.factory

import org.gradle.jacobo.schema.BaseSchemaDocument

interface DocumentFactory {
  public BaseSchemaDocument createDocument(File document)
}