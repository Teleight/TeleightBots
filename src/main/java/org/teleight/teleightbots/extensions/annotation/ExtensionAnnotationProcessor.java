package org.teleight.teleightbots.extensions.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.teleight.teleightbots.extensions.ExtensionManager;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes(value = "org.teleight.teleightbots.extensions.annotation.ExtensionInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ExtensionAnnotationProcessor extends AbstractProcessor {

    private ProcessingEnvironment environment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.environment = processingEnv;
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ExtensionInfo.class)) {
            if (!(element instanceof TypeElement typeElement)) {
                continue;
            }
            final Name qualifiedName = typeElement.getQualifiedName();
            final String mainClass = qualifiedName.toString();

            final ExtensionInfo extension = typeElement.getAnnotation(ExtensionInfo.class);
            final ExtensionInfoFile description = ExtensionInfoFile.fromAnnotation(extension, mainClass);
            try {
                final FileObject object = environment.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", ExtensionManager.EXTENSION_FILE);
                try (Writer writer = new BufferedWriter(object.openWriter())) {
                    new ObjectMapper().writeValue(writer, description);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
