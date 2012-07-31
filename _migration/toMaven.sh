#!/bin/bash
#
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
#
# Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
#
# The contents of this file are subject to the terms of either the GNU
# General Public License Version 2 only ("GPL") or the Common Development
# and Distribution License("CDDL") (collectively, the "License").  You
# may not use this file except in compliance with the License.  You can
# obtain a copy of the License at
# https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
# or packager/legal/LICENSE.txt.  See the License for the specific
# language governing permissions and limitations under the License.
#
# When distributing the software, include this License Header Notice in each
# file and include the License file at packager/legal/LICENSE.txt.
#
# GPL Classpath Exception:
# Oracle designates this particular file as subject to the "Classpath"
# exception as provided by Oracle in the GPL Version 2 section of the License
# file that accompanied this code.
#
# Modifications:
# If applicable, add the following below the License Header, with the fields
# enclosed by brackets [] replaced by your own identifying information:
# "Portions Copyright [year] [name of copyright owner]"
#
# Contributor(s):
# If you wish your version of this file to be governed by only the CDDL or
# only the GPL Version 2, indicate your decision by adding "[Contributor]
# elects to include this software in this distribution under the [CDDL or GPL
# Version 2] license."  If you don't indicate a single choice of license, a
# recipient has the option to distribute your version of this file under
# either the CDDL, the GPL Version 2 or to extend the choice of license to
# its licensees as provided above.  However, if you add GPL Version 2 code
# and therefore, elected the GPL Version 2 license, then the option applies
# only if the new code is made subject to such option by the copyright
# holder.
#

if [ ! -e jaxws-ri ]; then
    echo "no jaxws-ri workspace"
    exit 1
fi

pushd jaxws-ri

modules=("eclipselink_jaxb" "eclipselink_sdo" "httpspi-servlet" "rt" rt-fi rt-ha servlet tools/wscompile transports/async-client-transport transports/local)

for module in ${modules[*]}; do
echo "=== Module $module ==="
pushd $module

#move sources
echo "-> moving sources"
dir=main/java

pushd src
mkdir -p $dir
for f in `find . -type f -name *.java`; do
    d=`dirname $f`
    if [ ! -e "$dir/$d" ]; then
        mkdir -p $dir/$d
        svn add --parents $dir/$d
    fi
    svn mv --parents "$f" "$dir/$d"
done

#move resources
echo "-> moving resources"
dir=main/resources

for f in `find -type f`; do

    if [[ "$f" == *svn* || "$f" == *main/java* ]]; then
        continue
    fi
    d=`dirname $f`

    if [ ! -e $dir/$d ]; then
        mkdir -p "$dir/$d"
        svn add --parents $dir/$d/
    fi
    svn mv --parents $f $dir/$d/
done

popd

#move test sources
if [ -e test ]; then
    echo "-> moving test sources"
    dir=../src/test/java

    pushd test
    mkdir -p $dir
    for f in `find . -type f -name *.java`; do
        d=`dirname $f`
        if [ ! -e "$dir/$d" ]; then
            mkdir -p $dir/$d
            svn add --parents $dir/$d
        fi
        svn mv --parents "$f" "$dir/$d"
    done

    #move test sources
    echo "-> moving test resources"
    dir=../src/test/resources
    for f in `find -type f`; do
        if [[ "$f" == *svn* ]]; then
            continue
        fi
        d=`dirname $f`
        if [ ! -e $dir/$d ]; then
            mkdir -p "$dir/$d"
            svn add --parents $dir/$d/
        fi
        svn mv --parents $f $dir/$d/
    done
    popd
    echo "-> removing test folder"
    svn rm test
fi

if [ -e lib ]; then
    echo "-> removing lib"
    svn rm lib
fi

if [ -e src/com ]; then
    echo "-> removing empty dirs"
    svn rm src/com
fi

if [ -e etc ]; then
    echo "-> moving META=INF resources"
    pushd etc
    dir=../src/main/resources/META-INF
    for f in `find -type f`; do
        if [[ "$f" == *svn* || "$f" == *manifest ]]; then
            continue
        fi
        if [[ "$f" == *.xml ]]; then
            d=./
        else
            d="services/"
        fi
        if [ ! -e $dir/$d ]; then
            mkdir -p "$dir/$d"
            svn add --parents $dir/$d/
        fi
        svn mv --parents "$f" "$dir/$d"
    done
    popd
    echo "-> removing etc"
    svn rm etc
fi

popd
done

#update pom.xmls

#move current ones to expected location to keep history...
#main
mkdir -p bundles/jaxws-rt bundles/jaxws-tools bom
svn add --parents bundles bom
svn mv --parents etc/poms/jaxws-ri.pom pom.xml
svn mv --parents etc/poms/jaxws-rt.pom bundles/jaxws-rt/pom.xml
svn mv --parents etc/poms/jaxws-tools.pom bundles/jaxws-tools/pom.xml
#plugins
svn mv --parents etc/poms/jaxws-eclipselink-plugin.pom eclipselink_jaxb/pom.xml
svn mv --parents etc/poms/sdo-eclipselink-plugin.pom eclipselink_sdo/pom.xml
#transports
svn mv --parents transports/async-client-transport/jaxws-async-client-transport.pom transports/async-client-transport/pom.xml
svn mv --parents transports/local/jaxws-local-transport.pom transports/local/pom.xml

popd

#replace old poms with new ones
for f in `find _migration/poms -name pom.xml`; do
    cp -v $f jaxws-ri/${f##_migration/poms/}
done

