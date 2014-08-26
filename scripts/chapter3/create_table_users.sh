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

# create_table_users.sh  
#

./sqlline.sh localhost $HBASE_BOOK_HOME/scripts/chapter3/create_table_users.sql