package org.codehaus.modello.plugin.java;

/*
 * Copyright (c) 2004, Jason van Zyl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.codehaus.modello.FileUtils;
import org.codehaus.modello.ModelloGeneratorTest;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.core.ModelloCore;
import org.codehaus.modello.model.Model;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class PackageVersionJavaTest
    extends ModelloGeneratorTest
{
    private String modelFile = "src/test/resources/models/maven.mdo";

    public PackageVersionJavaTest()
    {
        super( "packageversion" );
    }

    private File generatedSources;

    private File classes;

    public void testThatTheCorrectVersionIsInThePackageName()
        throws Throwable
    {
        generatedSources = new File( getTestPath( "target/" + getName() + "/sources" ) );

        classes = new File( getTestPath( "target/" + getName() + "/classes" ) );

        FileUtils.deleteDirectory( generatedSources );

        generatedSources.mkdirs();

        classes.mkdirs();

        ModelloCore modello = getModelloCore();

        Properties parameters = new Properties();

        parameters.setProperty( ModelloParameterConstants.OUTPUT_DIRECTORY, generatedSources.getAbsolutePath() );

        parameters.setProperty( ModelloParameterConstants.PACKAGE_WITH_VERSION, Boolean.toString( true ) );

        parameters.setProperty( ModelloParameterConstants.VERSION, "4.0.0" );

        Model model = loadModel( modelFile );

        modello.generate( model, "java", parameters );

        compile( generatedSources, classes );

        verify( "PackageVersionVerifier", "java" );
    }
}