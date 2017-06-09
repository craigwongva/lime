import jenkins.*

import jenkins.model.*

import hudson.*

import hudson.model.*

a=Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0];

b=(a.installations as List);

b.add(new hudson.tasks.Maven.MavenInstallation("M3.0.5", "/usr/local/apache-maven", []));

a.installations=b

a.save()
