#!/bin/sh
# Copyright 2014 Mark Kerzner.
# This file is part of HBase Design Patterns book for Packt (HDPP).
#
# HDPP is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# HDPP is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with HDPP.  If not, see <http://www.gnu.org/licenses/>.

# generate_users.sh  Run java code to generate SQL statements to insert users into HBase
#
# The script presupposes that HDPP project built with dependencies, with the following command
# mvn clean assembly:single
# parameters:   $1:   TODO
# parameters:   $2:   TODO

java -classpath ../target/cassandra-labs-1.0-SNAPSHOT-jar-with-dependencies.jar com.hi.cassandra.video.GenerateUsers $1 $2
