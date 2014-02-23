package com.gradle.plugins.jacobo.schema


import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names

import com.gradle.plugins.jacobo.schema.factory.DefaultDocumentFactory
import com.gradle.plugins.jacobo.schema.factory.DocumentFactory
import com.gradle.plugins.jacobo.schema.resolve.AbsoluteFileResolver
import com.gradle.plugins.jacobo.schema.resolve.DefaultAbsoluteFileResolver
import com.gradle.plugins.jacobo.schema.resolve.DefaultDocumentResolver
import com.gradle.plugins.jacobo.schema.resolve.DocumentResolver
import com.gradle.plugins.jacobo.schema.slurping.DefaultDocumentSlurper
import com.gradle.plugins.jacobo.schema.slurping.DefaultXsdSlurper
import com.gradle.plugins.jacobo.schema.slurping.DocumentSlurper
import com.gradle.plugins.jacobo.schema.slurping.XsdSlurper
import com.gradle.plugins.jacobo.schema.document.XsdDocument
import com.gradle.plugins.jacobo.schema.document.WsdlDocument
import com.gradle.plugins.jacobo.schema.document.BaseSchemaDocument
import com.gradle.plugins.jacobo.schema.document.SchemaDocumentFactory

class DocSlurperModule extends AbstractModule {
  
  @Override
  protected void configure() {
    // bind ___ interface to ----> ___ implementation
    bind(DocumentResolver).to(DefaultDocumentResolver);
    bind(XsdSlurper).to(DefaultXsdSlurper);
    bind(DocumentSlurper).to(DefaultDocumentSlurper);
    bind(AbsoluteFileResolver).to(DefaultAbsoluteFileResolver);
    bind(DocumentFactory).to(DefaultDocumentFactory);
    // schemadocumentfactory implement factory, return type BaseSchemaDocument
    // Name annotation returns particular implementation
    // build SchemaDocumentFactory <-- look for specifics
    install(new FactoryModuleBuilder()
    	    .implement(BaseSchemaDocument, Names.named("xsd"), XsdDocument)
    	    .implement(BaseSchemaDocument, Names.named("wsdl"), WsdlDocument)
    	    .build(SchemaDocumentFactory)
    	   )
  }
}