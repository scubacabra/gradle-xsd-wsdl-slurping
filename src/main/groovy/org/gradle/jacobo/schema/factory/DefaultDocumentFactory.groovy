package org.gradle.jacobo.schema.factory

import com.google.inject.Inject

import org.gradle.jacobo.schema.BaseSchemaDocument
import org.gradle.jacobo.schema.SchemaDocumentFactory

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