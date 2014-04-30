package org.gradle.jacobo.schema

import com.google.inject.name.Named
import groovy.util.slurpersupport.GPathResult

interface SchemaDocumentFactory {
  @Named("wsdl") BaseSchemaDocument createWsdlDocument(File wsdlFile, GPathResult slurpedWsdl)
  @Named("xsd") BaseSchemaDocument createXsdDocument(File xsdFile, GPathResult slurpedWsdl)
}