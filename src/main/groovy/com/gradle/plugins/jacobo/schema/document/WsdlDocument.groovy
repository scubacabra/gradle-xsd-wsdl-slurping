package com.gradle.plugins.jacobo.schema.document

import groovy.util.slurpersupport.GPathResult

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.gradle.plugins.jacobo.schema.slurping.DocumentSlurper
import com.gradle.plugins.jacobo.schema.resolve.DocumentResolver

class WsdlDocument extends BaseSchemaDocument {
  private static final Logger log = LoggerFactory.getLogger(WsdlDocument.class)

  /**
   * xsd import locations (relative to #documentFile currentDir)
   */
  def wsdlImports

  /**
   * xsd import locations (relative to #documentFile currentDir)
   */
  def xsdImports

  /**
   * xsd includes locations (relative to #documentFile currentDir)
   */
  def xsdIncludes

  @Inject
  WsdlDocument(DocumentSlurper documentSlurper, DocumentResolver documentResolver,
	       @Assisted File documentFile,
	       @Assisted GPathResult slurpedDocument) {
    super(documentSlurper, documentResolver, documentFile, slurpedDocument)
  }

  @Override
  public void slurp() {
    def importedWsdl = slurpedDocument?.import
    wsdlImports = documentSlurper.slurpDependencies(importedWsdl, documentFile,
						    "wsdl imports")
    def importedXsds = slurpedDocument?.types?.schema?.import
    xsdImports = documentSlurper.slurpDependencies(importedXsds, documentFile,
						   "xsd imports")
    def inludedXsds = slurpedDocument?.types?.schema?.include
    xsdIncludes = documentSlurper.slurpDependencies(inludedXsds, documentFile,
						    "xsd includes")
    documentDependencies = documentResolver.resolveRelativePaths(
      wsdlImports + xsdImports + xsdIncludes, documentFile.parentFile)
  }
}