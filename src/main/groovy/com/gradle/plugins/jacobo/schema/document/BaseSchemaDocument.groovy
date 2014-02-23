package com.gradle.plugins.jacobo.schema.document

import groovy.util.slurpersupport.GPathResult

import com.google.inject.Inject

import com.gradle.plugins.jacobo.schema.resolve.DocumentResolver
import com.gradle.plugins.jacobo.schema.slurping.DocumentSlurper

abstract class BaseSchemaDocument implements SchemaDocument {
  /**
   * Absolute File Object to Document slurped
   */
  File documentFile

  /**
   * Slurped Object that slurped the document file
   */
  GPathResult slurpedDocument

  /**
   * absolute File Object HashMap.  document relative dependent string
   * Key - absolute document File location on file system
   */
  Map<String, File> documentDependencies

  /**
   * Document Slurper Object that does the slurping of the document
   */
  DocumentSlurper documentSlurper

  /**
   * Document Resolver Object that performs the dependency resolution
   */
  DocumentResolver documentResolver

  @Inject
  BaseSchemaDocument(DocumentSlurper documentSlurper,
		     DocumentResolver documentResolver, File documentFile,
		     GPathResult slurpedDocument) {
    this.documentSlurper = documentSlurper
    this.documentResolver = documentResolver
    this.documentFile = documentFile
    this.slurpedDocument = slurpedDocument
  }

  public abstract void slurp()
}