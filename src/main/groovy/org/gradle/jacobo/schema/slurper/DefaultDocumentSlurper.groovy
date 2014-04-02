package org.gradle.jacobo.schema.slurper

import groovy.util.slurpersupport.GPathResult

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DefaultDocumentSlurper implements DocumentSlurper {
  private static final Logger log = LoggerFactory.getLogger(DefaultDocumentSlurper.class)

  /**
   * Slurp the (relative) dependencies in the document.
   * Return a set of relative dependency path files this
   * documen depends on.
   * @param dependencies The nodechildren dependencies to slurp up
   * @param documentFile the file that is being slurped through
   * @param dependencyType the type of dependency being slurped
   *   i.e. for wsdl "wsdl import", "xsd imports", "xsd includes"
   *   i.e. for xsd "imports" and "includes"
   *   dependencyType is further used for a special wsdl import processing
   *   the WSDL spec designated the attribute 'location' to hold the location of
   *   the imported wsdl, and NOT 'schemaLocation' as for xsd's.
   * @return Set of relative path file strings.
   */
  @Override
  public Set<String> slurpDependencies(GPathResult dependencies,
				       File documentFile, String dependencyType) {
    def relativeDependencies = [] as Set
    def dependencySize = dependencies.size()
    if(dependencySize == 0) { 
      log.debug("'{}' has no dependencies for '{}'", documentFile, dependencyType)
      return relativeDependencies
    }
    log.debug("slurping '{}' dependencies for '{}'", dependencySize, dependencyType)
    dependencies.each { dependency ->
      if (dependencyType.equals("wsdl imports")) {
	relativeDependencies.add(dependency.@location.text())
	return
      }
      relativeDependencies.add(dependency.@schemaLocation.text())
      // TODO any warnings if there is no schemaLocation i.e. malformed xsd/wsdl file?
    }
    return relativeDependencies
  } 
}