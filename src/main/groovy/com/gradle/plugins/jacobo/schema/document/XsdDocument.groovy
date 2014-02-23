package com.gradle.plugins.jacobo.schema.document

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted

import groovy.util.slurpersupport.GPathResult

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.gradle.plugins.jacobo.schema.slurping.DocumentSlurper
import com.gradle.plugins.jacobo.schema.slurping.XsdSlurper
import com.gradle.plugins.jacobo.schema.resolve.DocumentResolver

class XsdDocument extends BaseSchemaDocument {
  private static final Logger log = LoggerFactory.getLogger(XsdDocument.class)

  /**
   * xsd Slurper Object that does Xsd Specific slurping
   */
  XsdSlurper xsdSlurper

  /**
   * xsd import locations (relative to #documentFile currentDir)
   */
  Set<String> xsdImports

  /**
   * xsd includes locations (relative to #documentFile currentDir)
   */
  Set<String> xsdIncludes

  /**
   * xsd namespace of this document
   */
  String xsdNamespace

  @Inject
  XsdDocument(DocumentSlurper documentSlurper, DocumentResolver documentResolver,
	      XsdSlurper xsdSlurper, @Assisted File documentFile,
	      @Assisted GPathResult slurpedDocument) {
    super(documentSlurper, documentResolver, documentFile, slurpedDocument)
    this.xsdSlurper = xsdSlurper
  }

  @Override
  public void slurp() {
    def imports = slurpedDocument?.import
    this.xsdImports = documentSlurper.slurpDependencies(imports, documentFile,
							"imports")
    def includes = slurpedDocument?.include
    xsdIncludes = documentSlurper.slurpDependencies(includes, documentFile,
						    "includes")
    xsdNamespace = xsdSlurper.slurpNamespace(slurpedDocument, documentFile)
    documentDependencies = documentResolver.resolveRelativePaths(
      xsdImports + xsdIncludes, documentFile.parentFile)
  }

  def findResolvedXsdIncludes() { 
    return xsdSlurper.findResolvedXsdIncludes(xsdNamespace, documentFile.name,
					      xsdIncludes, documentDependencies)
  }

  def findResolvedXsdImports() { 
    return xsdSlurper.findResolvedXsdImports(xsdNamespace, xsdImports,
					     documentDependencies)
  }
}