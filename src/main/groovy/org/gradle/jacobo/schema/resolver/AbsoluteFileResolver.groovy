package org.gradle.jacobo.schema.resolver

interface AbsoluteFileResolver {
  
  /**
   * Resolves a string path relative to a certain parent directory into a
   * File object containing the absolute Path with no relative path
   * characters.
   * 
   * String path can contain any of the symbols used for moving up
   * directories. '../../someDir' or for continuing deeper in a
   * directory './someDir' or just 'someDir'.  Returned file object
   * will contain the absolute path to this  Directory that the parsed
   * Document resides in (minus these relative path characters)
   * 
   * @param relativeLocation relative string pointing to a new relative
   *   location
   * @param parentDirectory the parent directory to find the relative
   *   location to
   * @return File absolute file path of this relativeLocation
   */
  public File resolveToAbsolutePath(String relativeLocation,
				    File parentDirectory)
}