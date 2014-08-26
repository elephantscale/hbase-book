#!/bin/sh
#
# Copyright Mark Kerzner
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# stock_example.sh  Run a simple example coming with the Phoenix distribution
#
# parameters:   $1:   None
# The script presupposes the Phoenix files extracted to the right places, and is run from the 'bin' directory
# It also assumes that you have set the environmental variable HBASE_BOOK_HOME to point to the directory
# where you installed the HDPP.

./sqlline.sh localhost $HBASE_BOOK_HOME/scripts/chapter2/stock_symbol.sql