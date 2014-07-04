var JEC = new function() {

	
	this.savedValues = null;

	this.set = function (name, value) {
		this._saveECToCookieStorage(name, value);
		this._saveECToGlobalStorage(name, value);
		this._saveECToLocalStorage(name, value);
		this._saveECToSessionStorage(name, value);
		this._saveECToDatabaseStorage(name, value);
		this._saveECToUserdataStorage(name, value);
		this._saveECToWindowStorage(name, value);
	};

	this.get = function(name, callback) {
		this.savedValues = new Array();
		this.savedValues["${jevercookie.cookieStorage}"] = this._loadECFromCookieStorage(name);
		this.savedValues["${jevercookie.globalStorage}"] = this._loadECFromGlobalStorage(name);
		this.savedValues["${jevercookie.localStorage}"] = this._loadECFromLocalStorage(name);
		this.savedValues["${jevercookie.sessionStorage}"] = this._loadECFromSessionStorage(name);
		this.savedValues["${jevercookie.userdataStorage}"] = this._loadECFromUserdataStorage(name);
		this.savedValues["${jevercookie.windowStorage}"] = this._loadECFromWindowStorage(name);

		this._loadECFromDatabaseStorage(name);

		setTimeout(function() {
			JEC._waitForLoadingValues(callback, 0);
		}, this.TIMEOUT);
	};

	this._waitForLoadingValues = function(callback, recursionDepth) {
		var isAllValsLoaded = true;
		isAllValsLoaded &= this._isSavedECToDatabaseStorage();
		if (!isAllValsLoaded) {
			if (recursionDepth < ${jevercookie.maxRecursionDepth}) {
				setTimeout(function() {
					JEC._waitForLoadingValues(callback, recursionDepth + 1);
				}, ${jevercookie.timeout});
				return;
			};
		}

		var bestValue = this._getBestECValue(this.savedValues);
		
		this._saveBestECToCookieStorage(name, bestValue);
		this._saveBestECToGlobalStorage(name, bestValue);
		this._saveBestECToLocalStorage(name, bestValue);
		this._saveBestECToSessionStorage(name, bestValue);
		this._saveBestECToUserdataStorage(name, bestValue);
		this._saveBestECToWindowStorage(name, bestValue);
		this._saveBestECToDatabaseStorage(name, bestValue);

		if (typeof callback === "function") {
			callback(bestValue);
		}
	};

	this._getBestECValue = function(ecs) {
		var candidates = new Array();
		for (var i in ecs) {
			if (ecs[i] && ecs[i] !== null && ecs[i] !== undefined) {
				candidates[ecs[i]] = (candidates[ecs[i]] === undefined) ? 1
						: candidates[ecs[i]] + 1;
			}
		}
		var maxCount = 0;
		var bestECValue = null;
		for ( var i in candidates) {
			if (candidates[i] > maxCount) {
				maxCount = candidates[i];
				bestECValue = i;
			}
		}
		return bestECValue;
	};
	


    this._saveECToCookieStorage = function(name, value) {
		document.cookie = name + "=; expires=Mon, 20 Sep 2010 00:00:00 UTC; domain=${jevercookie.cookie.domain}; path=${jevercookie.cookie.path}";
		document.cookie = name + "=" + value + "; expires=${jevercookie.cookie.expires}; domain=${jevercookie.cookie.domain}; path=${jevercookie.cookie.path}";
	};

	this._saveBestECToCookieStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.cookieStorage}"]) {
			this._saveECToCookieStorage(name, bestValue);
		} 
	};
	
	this._loadECFromCookieStorage = function(name) {
		return this._getValueFromParamStr(document.cookie, name);
	};

	this._saveECToGlobalStorage = function(name, value) {
		try {
			this.globalStorage[location.host][name] = value;
		} catch (e) {}
	};

	this._saveBestECToGlobalStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.globalStorage}"]) {
			this._saveECToGlobalStorage(name, bestValue);
		} 
	};

	this._loadECFromGlobalStorage = function(name) {
		try {
			return this.globalStorage[location.host][name];
		} catch (e) {
			return null;
		}
	};
	
	this._saveECToLocalStorage = function(name, value) {
		try {
			this.localStorage.setItem(name, value);
		} catch (e) {}
	};

	this._saveBestECToLocalStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.localStorage}"]) {
			this._saveECToLocalStorage(name, bestValue);
		} 
	};

	this._loadECFromLocalStorage = function(name) {
		try {
			return this.localStorage.getItem(name);
		} catch (e) {
			return null;
		}
	};

	this._saveECToSessionStorage = function(name, value) {
		try {
			this.sessionStorage.setItem(name, value);
		} catch (e) {}
	};


	this._saveBestECToSessionStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.sessionStorage}"]) {
			this._saveECToSessionStorage(name, bestValue);
		} 
	};

	this._loadECFromSessionStorage = function(name) {
		try {
			return this.sessionStorage.getItem(name);
		} catch (e) {
			return null;
		}
	};
	
	this._saveECToDatabaseStorage = function(name, value) {
		try {
			var db = window.openDatabase("${jevercookie.database.name}", "", "", 1024 * 1024);
			db.transaction(function(tx) {
				tx.executeSql("CREATE TABLE IF NOT EXISTS ${jevercookie.database.table} ("
						+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
						+ "${jevercookie.database.nameField} TEXT NOT NULL, ${jevercookie.database.valueField}"
						+ " TEXT NOT NULL, " + "UNIQUE (${jevercookie.database.nameField})" + ")",
						[], function(tx, rs) {}, function(tx, err) {});
				tx.executeSql("INSERT OR REPLACE INTO ${jevercookie.database.table} ("
						+ "${jevercookie.database.nameField}, ${jevercookie.database.valueField}) " + "VALUES(?, ?)", [name, value], function(tx, rs) {}, function(tx, err) {});
			});
		} catch (e) {}
	};

	this._saveBestECToDatabaseStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.databaseStorage}"]) {
			this._saveECToDatabaseStorage(name, bestValue);
		} 
	};

	this._isSavedECToDatabaseStorage = function() {
		if (this.savedValues["${jevercookie.databaseStorage}"] == null) {
			return false;
		} else {
			return true;
		}
	};

	this._loadECFromDatabaseStorage = function(name) {
		try {
			var db = window.openDatabase("${jevercookie.database.name}", "", "", 1024 * 1024);
			db.transaction(function(tx) {
				tx.executeSql("SELECT ${jevercookie.database.valueField} FROM ${jevercookie.database.table}"
						+ " WHERE ${jevercookie.database.nameField}=?", [name], function(tx,	result1) {
					if (result1.rows.length >= 1) {
						this.savedValues["${jevercookie.databaseStorage}"] = result1.rows.item(0).value;
					}
				}, function(tx, err) {});
			});
		} catch (e) {}
	};

	this._saveECToUserdataStorage = function(name, value) {
		var element = this._createOrGetElement("div", "${jevercookie.userdata.element}");
        element.setAttribute(name, value);
	};

	this._saveBestECToUserdataStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.userdataStorage}"]) {
			this._saveECToUserdataStorage(name, bestValue);
		} 
	};

	this._loadECFromUserdataStorage = function(name) {
		var element = this._getElement("${jevercookie.userdata.element}");
		if (element)
			return element.getAttribute(name);
		else
			return null;
	};
	
	this._saveECToWindowStorage = function(name, value) {
		window.name = this._replaceInParamStr(window.name, name, value);
	};

	this._saveBestECToWindowStorage = function(name, bestValue) {
		if (bestValue != this.savedValues["${jevercookie.windowStorage}"]) {
			this._saveECToWindowStorage(name, bestValue);
		} 
	};

	this._loadECFromWindowStorage = function(name) {
		return this._getValueFromParamStr(window.name, name);
	};
	
	this._getValueFromParamStr = function (str, name) {
        var nameAndEqual = name + "=";
        var strItems = str.split(/[;&]/);
        for (var i = 0; i < strItems.length; i++) {
            var strItem = strItems[i];
            while (strItem.charAt(0) === " ") {
            	strItem = strItem.substring(1, strItem.length);
            }
            if (strItem.indexOf(nameAndEqual) === 0) {
                return strItem.substring(nameAndEqual.length, strItem.length);
            }
        }
        return null;
    };
    
    this._replaceInParamStr = function (str, key, value) {
    	var keyEquals = key + "=";
    	var beg = str.indexOf(keyEquals);
    	if (beg > -1) {
    		var end = str.indexOf("&", beg + 1);
    		var preStr = str.substr(0, beg);
    		var postStr = "";
    		if (end !== -1) {
    			postStr	= str.substr(end);
    		}
    		return preStr + key + "=" + value + postStr;
        } else {
            return str + "&" + key + "=" + value;
        }
    };
    
    this._createOrGetElement = function (type, name) {
        var element = document.getElementById(name);
        if (!element) {
        	element = document.createElement(type);
        }
        element.style.visibility = "hidden";
        element.style.position = "absolute";
       	element.setAttribute("id", name);
       	document.body.appendChild(element);
        return element;
    };
    
    this._getElement = function (name) {
    	return document.getElementById(name);
    }

};
