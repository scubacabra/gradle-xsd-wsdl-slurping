package org.gradle.jacobo.schema.resolver

interface DocumentResolver {
  
  /**
   * Resolve relative dependencies to absolute file objects relative to the
   * Directory that the parsed Document resides in.
   *   i.e. parsed file @ /some/dir/file.xsd and has dependencies of
   *     ../otherDir/other.xsd and subDir/sub.xsd then these relative paths
   *     would be resolved to /some/otherDir/other.xsd and
   *     /some/dir/subDir/sub.xsd.
   *
   * Uses AbsoluteFileResolver to resolve relative paths
   * 
   * @param relative dependency paths to resolve (both
   *   imports and includes dependencies)
   * @param documentDirectory directory of the parsed schema document
   * @return map where Relative dependency is a key and absolute
   *   file object is a value, for lookup later
   */
  public Map<String, File> resolveRelativePaths(
    Set<String> relativeDependencies,
    File documentDirectory)
}