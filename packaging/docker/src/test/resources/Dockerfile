FROM docker.io/jboss/wildfly

MAINTAINER Luigi Fugaro lfugaro@redhat.com

#USER root
#RUN yum install -y net-tools

USER jboss

#ADD ./gfoundries-config-eap.sh /opt/jboss/wildfly/
#ADD ./gfoundries-roles.properties /opt/jboss/wildfly/standalone/configuration/application-roles.properties
#ADD ./gfoundries-users.properties /opt/jboss/wildfly/standalone/configuration/application-users.properties
ADD ./gfoundries-config-eap.sh ./gfoundries-roles.properties ./gfoundries-users.properties /opt/jboss/wildfly/gfoundries/

RUN ls /opt/jboss/wildfly/gfoundries/

RUN whoami

USER root

RUN chmod +x /opt/jboss/wildfly/gfoundries/gfoundries-config-eap.sh

USER jboss

RUN ["/opt/jboss/wildfly/gfoundries/gfoundries-config-eap.sh"]
RUN rm -rf  /opt/jboss/wildfly/standalone/configuration/standalone_xml_history

EXPOSE 8787 8080 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--debug", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]


