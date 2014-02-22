package com.gradle.plugins.jacobo.schema.slurping

import groovy.util.slurpersupport.GPathResult

interface DocumentSlurper {
  
  /**
   * Slurp the (relative) dependencies in the document.
   * Return a set of relative dependency path files this
   * documen depends on.
   * @param dependencies The nodechildren dependencies to slurp up
   * @param documentFile the file that is being slurped through
   * @param dependencyType the type of dependency being slurped
   *   i.e. wsdl -- import, wsdl -- xsd import, wsdl -- xsd include
   *        xsd -- import, xsd -- include
   * @return Set of relative path file strings.
   */
  public Set<String> slurpDependencies(GPathResult dependencies,
				       File documentFile, String dependencyType)
}