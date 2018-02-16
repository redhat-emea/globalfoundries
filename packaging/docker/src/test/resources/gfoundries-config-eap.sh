#!/bin/bash

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CUSTOMIZATION=$JBOSS_HOME/gfoundries
JBOSS_STANDALONE_CONFIG=$JBOSS_HOME/standalone/configuration/
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=standalone
JBOSS_CONFIG=$JBOSS_MODE.xml

echo "=> Adding WildFly admin user"
$JBOSS_HOME/bin/add-user.sh admin admin

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
export GFOUNDRIES_DS="java:/GFoundriesDS"
export H2_URI="jdbc:h2:mem:GFOUNDRIES_DB;DB_CLOSE_DELAY=-1"
export H2_USER="sa"
export H2_PWD="sa"

$JBOSS_CLI -c << EOF
batch
echo "Connection URL: " $CONNECTION_URL
# First step : Add the datasource
data-source add --name=GFoundriesDS --driver-name=h2 --jndi-name=$GFOUNDRIES_DS --connection-url=$H2_URI --user-name=$H2_USER --password=$H2_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000
# Then configure a realm that relies on property files
/subsystem=security/security-domain=gfoundries-sd:add(cache-type=default)
/subsystem=security/security-domain=gfoundries-sd/authentication=classic:add()
/subsystem=security/security-domain=gfoundries-sd/authentication=classic/login-module=UsersRoles:add(code=UsersRoles,flag=required,module-options={"usersProperties"=>"${JBOSS_CUSTOMIZATION}/gfoundries-users.properties","rolesProperties"=>"${JBOSS_CUSTOMIZATION}/gfoundries-roles.properties"})
# Execute the batch
run-batch
EOF

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
