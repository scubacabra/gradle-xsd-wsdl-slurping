package com.gradle.plugins.jacobo.schema.document

import groovy.util.slurpersupport.GPathResult

interface SchemaDocumentFactory {
  BaseSchemaDocument createWsdlDocument(File wsdlFile, GPathResult slurpedWsdl)
  BaseSchemaDocument createXsdDocument(File wsdlFile, GPathResult slurpedWsdl)
}