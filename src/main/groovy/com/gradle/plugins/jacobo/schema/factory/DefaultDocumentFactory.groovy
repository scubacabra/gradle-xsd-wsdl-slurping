package com.gradle.plugins.jacobo.schema.factory

import com.google.inject.Inject

import com.gradle.plugins.jacobo.schema.document.BaseSchemaDocument
import com.gradle.plugins.jacobo.schema.document.SchemaDocumentFactory

class DefaultDocumentFactory implements DocumentFactory {
  
  SchemaDocumentFactory schemaDocumentFactory

  @Inject
  DefaultDocumentFactory(SchemaDocumentFactory schemaDocFactory) {
    schemaDocumentFactory = schemaDocFactory
  }
  
  public BaseSchemaDocument createDocument(File document) {
    def slurped = new XmlSlurper().parse(document)
    // is xsd file?
    if (document.name[-3..-1] == "xsd") {
      def xsdDoc = schemaDocumentFactory.createXsdDocument(document, slurped)
      xsdDoc.slurp()
      return xsdDoc
    }
    // is wsdl file?
    if (document.name[-4..-1] == "wsdl") {
      def wsdlDoc = schemaDocumentFactory.createWsdlDocument(document, slurped)
      wsdlDoc.slurp()
      return wsdlDoc
    }

    thrown new Exception("trying to parse $document -- Can only parse .wsdl or .xsd files")
  }
}