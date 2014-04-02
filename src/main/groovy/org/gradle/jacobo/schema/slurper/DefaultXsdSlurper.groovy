package org.gradle.jacobo.schema.slurper

import groovy.util.slurpersupport.GPathResult

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DefaultXsdSlurper implements XsdSlurper {
  private static final Logger log = LoggerFactory.getLogger(DefaultXsdSlurper.class)

  /**
   * Slurp the namespace from the XmlSlurper object
   * Check for @targetNamespace
   * Or check for xsd:tns if no targetNamesapce
   * @param slurpedDocument the slurped xsd document
   * @param documentFile the xsd document file
   * @return namespace from document as a string
   */
  @Override
  public String slurpNamespace(GPathResult slurpedDocument, File documentFile) {
    def namespace = slurpedDocument?.@targetNamespace.text() 
    if (namespace) return namespace
    // TODO try to find xsd:tns as well.
    def warning = ["There is no targetNamespace attribute for file '{}' ",
		   "(assigning filname as namespace to it). A Schema ",
		   "should ALWAYS include a targetNamespace attribute at ",
		   "its root element.  No targetNamespace are categorized ",
		   "as using the Chameleon Design Pattern, which is not ",
		   "an advisable pattern, AT ALL!"]
    def warningText = ""
    warning.each { warningText += it }
    log.warn(warningText, documentFile)
    return documentFile.name
  }
  
  /**
   * Find resolved Xsd Imports.  Need the absolute path
   * for parsing the next dependencies and so forth
   * Finds the absolute File Objects by the relative path keys
   * @param namespace - the xsd namespace
   * @param fileName - the xsd file name
   * @param includedDependencies the included dependencies of the xsdDocument
   * @param absoluteDependencies the absolute dependency map of the xsdDocument
   * @return Set of File objects where the dependencies are located
   */
  @Override
  public Set<File> findResolvedXsdImports(
    String namespace, Set<String> importedDependencies,
    Map<String, File> absoluteDependencies) {
    log.debug("'{}' has relative imports '{}' that correspond to '{}'",
	      namespace, importedDependencies, absoluteDependencies.values())
    def resolvedImports = resolveDependencies(importedDependencies,
						   absoluteDependencies)
    return resolvedImports as Set
  }

  /**
   * Find resolved Xsd Includes.  Need the absolute path
   * for parsing the next dependencies and so forth
   * Finds the absolute File Objects by the relative path keys
   * @param namespace - the xsd namespace
   * @param fileName - the xsd file name
   * @param includedDependencies the included dependencies of the xsdDocument
   * @param absoluteDependencies the absolute dependency map of the xsdDocument
   * @return Set of File objects where the dependencies are located
   */
  @Override
  public Set<File> findResolvedXsdIncludes(
    String namespace, String fileName, Set<String> includedDependencies,
    Map<String, File> absoluteDependencies) {
    log.debug("'{}'@'{}' has relative includes '{}' that correspond to '{}'",
	      namespace, fileName, includedDependencies,
	      absoluteDependencies.values())
    def resolvedIncludes = resolveDependencies(includedDependencies,
					       absoluteDependencies)
    return resolvedIncludes as Set
  }
  
  /**
   * Actual resolving of a list of dependencies
   */
  def resolveDependencies(Set<String> dependencies, Map<String, File> absoluteDependencies) {
    def resolvedDependencies = dependencies.collect { dependency ->
      // this should NEVER happen, because the relative deps are resolved to
      // the absolute dependency map with the relative deps as the key
      // But just as a precaution, throw a warning if this happens, which it shouldnt.
      // TODO take out, and if take out, make a simple closure instead to pass around?
      if(!absoluteDependencies.containsKey(dependency)) {
	log.warn("THE DEPENDENCY '{}' ISN'T IN THE ABSOLUTE FILE HASHMAP '{}'",
		 dependency, absoluteDependencies)
	return null
      }
      absoluteDependencies.get(dependency)
    }
    return resolvedDependencies
  }
}
