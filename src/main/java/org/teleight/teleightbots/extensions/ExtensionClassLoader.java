package org.teleight.teleightbots.extensions;

import java.net.URL;
import java.net.URLClassLoader;

public class ExtensionClassLoader extends URLClassLoader {

    public ExtensionClassLoader(URL[] urls) {
        super(urls);
    }

    public ExtensionClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void addURL(URL url) {
        super.addURL(url);
    }

}
