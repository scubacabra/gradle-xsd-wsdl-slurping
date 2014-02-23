Documentation
=============

This is a library for two gradle plugins that share these common
objects.

Basically the library provides support for slurping the dependencies
of either an `xsd` or a `wsdl` file.  If there is a bad dependency in
a file that lists a file with an extension that isn't of `.xsd` or
`.wsdl` then an exception will be thrown.

Slurping
--------

### Xsd Slurping
Just go through the file and get the `xsd:import` and `xsd:include`
tags.  Parsing the `schemaLocation` attribute.

### Wsdl Slurping
Go through and parse the file, getting the `wsdl:import`,
`xsd:import`, and the `xsd:include`.  For `wsdl:import`, the attribute
to parse is `location`, while the xsd dependencies in a wsdl document
are as above.

Relative Dependencies
---------------------

The dependencies that are slurped are **relative**.  That is they are
listed in the document relative to the documents current position in
the file system.  The services `DocumentResolver` and
`AbsoulteFileResolver` handle the resolution of these relative
dependencies into absolute dependencies.

### Document Dependencies
Every `XsdDocument` and `WsdlDocument` have an instance field called
`documentDependencies` that is a map of the **relative** paths as keys
and the resolved files as values. The `gradle-jaxb-plugin` needs
access to the relative declaration **AND** the resolved files, where
as the `gradle-wsdl-plugin` only needs access to the resolved files.
Hence an extra variable to keep track of what is common to both.

Guicing it up
-------------

Deciding to use Guice, and dependency inject.  The `XsdDocument` and
`WsdlDocument` are just POGO's.  They are composed with services to do
the slurping and resolving.  Here is the class Diagram to see the
dependencies visually.

[![class-diagram](./img/uml-class-diagram-small.jpg "uml class
diagram")](./img/uml-class-diagram.png)


Xsd Processing Flow
-------------------

[![class-diagram](./img/xsd-processing-small.jpg "xsd
processing")](./img/xsd-processing.png)


Wsdl Processing flow
--------------------

[![class-diagram](./img/wsdl-processing-small.jpg "wsdl
processing")](./img/wsdl-processing.png)

