/*
 *    Copyright 2012 Chris Sinjakli
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;

public class RunHelperCommandParameterValues implements IParameterValues {

	@Override
	public Map<String, String> getParameterValues() {
		final HashMap<String, String> paramaterMap = new HashMap<String, String>();
		paramaterMap.put("Run", "RUN");
		paramaterMap.put("Debug", "DEBUG");
		return paramaterMap;
	}

}
