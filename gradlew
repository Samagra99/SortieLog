#!/bin/sh
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

APP_HOME=$(cd "${0%/*}" && pwd)
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS.
DEFAULT_JVM_OPTS=""

APP_HOME=$(cd "${0%/*}" && pwd)

exec "${APP_HOME}/gradle/wrapper/gradle-wrapper.jar" "$@"
