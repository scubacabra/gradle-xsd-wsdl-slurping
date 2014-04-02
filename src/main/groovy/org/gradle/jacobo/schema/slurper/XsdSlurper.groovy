package org.gradle.jacobo.schema.slurper

import groovy.util.slurpersupport.GPathResult

interface XsdSlurper {
  
  /**
   * Slurp the namespace from the XmlSlurper object
   * Check for @targetNamespace
   * Or check for xsd:tns if no targetNamesapce
   * @param slurpedDocument GPathResult slurped document to query
   * @param documentFile the file that the xmlslurper slurped up
   * @return namespace from document as a string
   */
  public String slurpNamespace(GPathResult slurpedDocument, File documentFile)
  
  /**
   * Find resolved Xsd Imports.  Need the absolute path
   * for parsing the next dependencies and so forth
   * Finds the absolute File Objects by the relative path keys
   * @param
   * @param
   * @return Set of File objects where the dependencies are located
   */
  public Set<File> findResolvedXsdImports(String namespace, 
					  Set<String> importedDependencies,
					  Map<String, File> absoluteDependencies)

  /**
   * Find resolved Xsd Includes.  Need the absolute path
   * for parsing the next dependencies and so forth
   * Finds the absolute File Objects by the relative path keys
   * @param
   * @param
   * @return Set of File objects where the dependencies are located
   */
  public Set<File> findResolvedXsdIncludes(String namespace, String fileName,
					   Set<String> includedDependencies,
					   Map<String, File> absoluteDependencies)
}