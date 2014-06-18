var JEC = new function() {

	this.MAX_RECURSION_DEPTH = 16;
	this.TIMEOUT = 100;

	this.DB_NAME = "hc6RTD9j";
	this.TABLE_NAME = "VO59Eh78";
	this.NAME_FIELD = "name";
	this.VALUE_FIELD = "value";
	
	this.USERDATA_ELEMENT = "nu5DRh09";

	this.COOKIE_STORAGE = "cookieStorage";
	this.GLOBAL_STORAGE = "globalStorage";
	this.LOCAL_STORAGE = "localStorage";
	this.SESSION_STORAGE = "sessionStorage";
	this.USERDATA_STORAGE = "userdataStorage";
	this.WINDOW_STORAGE = "windowStorage";
	this.DATABASE_STORAGE = "databaseStorage";

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
		
		this.savedValues[this.COOKIE_STORAGE] = this._loadECFromCookieStorage(name);
		this.savedValues[this.GLOBAL_STORAGE] = this._loadECFromGlobalStorage(name);
		this.savedValues[this.LOCAL_STORAGE] = this._loadECFromLocalStorage(name);
		this.savedValues[this.SESSION_STORAGE] = this._loadECFromSessionStorage(name);
		this.savedValues[this.USERDATA_STORAGE] = this._loadECFromUserdataStorage(name);
		this.savedValues[this.WINDOW_STORAGE] = this._loadECFromWindowStorage(name);

		this._loadECFromDatabaseStorage(name);

		setTimeout(function() {
			JEC._waitForLoadingValues(callback, 0);
		}, this.TIMEOUT);
	};

	this._waitForLoadingValues = function(callback, recursionDepth) {
		var isAllValsLoaded = true;
		isAllValsLoaded &= this._isSavedECToDatabaseStorage();
		if (!isAllValsLoaded) {
			if (recursionDepth < this.MAX_RECURSION_DEPTH) {
				setTimeout(function() {
					JEC._waitForLoadingValues(callback, recursionDepth + 1);
				}, this.TIMEOUT);
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
		document.cookie = name + "=; expires=Mon, 20 Sep 2010 00:00:00 UTC";
		document.cookie = name + "=" + value + ";";
	};

	this._saveBestECToCookieStorage = function(name, bestValue) {
		if (bestValue != this.savedValues[this.COOKIE_STORAGE]) {
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
		if (bestValue != this.savedValues[this.GLOBAL_STORAGE]) {
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
		if (bestValue != this.savedValues[this.LOCAL_STORAGE]) {
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
		if (bestValue != this.savedValues[this.SESSION_STORAGE]) {
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
			var db = window.openDatabase(this.DB_NAME, "", "", 1024 * 1024);
			db.transaction(function(tx) {
				tx.executeSql("CREATE TABLE IF NOT EXISTS " + this.TABLE_NAME + "("
						+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
						+ this.NAME_FIELD + " TEXT NOT NULL, " + this.VALUE_FIELD
						+ " TEXT NOT NULL, " + "UNIQUE (" + this.NAME_FIELD + ")" + ")",
						[], function(tx, rs) {}, function(tx, err) {});
				tx.executeSql("INSERT OR REPLACE " + "INTO " + this.TABLE_NAME + "("
						+ this.NAME_FIELD + ", " + this.VALUE_FIELD + ") " + "VALUES(?, ?)", [name, value], function(tx, rs) {}, function(tx, err) {});
			});
		} catch (e) {}
	};

	this._saveBestECToDatabaseStorage = function(name, bestValue) {
		if (bestValue != this.savedValues[this.DATABASE_STORAGE]) {
			this._saveECToDatabaseStorage(name, bestValue);
		} 
	};

	this._isSavedECToDatabaseStorage = function() {
		if (this.savedValues[this.DATABASE_STORAGE] == null) {
			return false;
		} else {
			return true;
		}
	};

	this._loadECFromDatabaseStorage = function(name) {
		try {
			var db = window.openDatabase(this.DB_NAME, "", "", 1024 * 1024);
			db.transaction(function(tx) {
				tx.executeSql("SELECT " + this.VALUE_FIELD + " " + "FROM " + this.TABLE_NAME
						+ " " + "WHERE " + this.NAME_FIELD + "=?", [name], function(tx,	result1) {
					if (result1.rows.length >= 1) {
						this.savedValues[this.DATABASE_STORAGE] = result1.rows.item(0).value;
					}
				}, function(tx, err) {});
			});
		} catch (e) {}
	};
	
	this._saveECToUserdataStorage = function(name, value) {
		var element = this._createOrGetElement("div", this.USERDATA_ELEMENT);
        element.setAttribute(name, value);
	};

	this._saveBestECToUserdataStorage = function(name, bestValue) {
		if (bestValue != this.savedValues[this.USERDATA_STORAGE]) {
			this._saveECToUserdataStorage(name, bestValue);
		} 
	};

	this._loadECFromUserdataStorage = function(name) {
		var element = this._getElement(this.USERDATA_ELEMENT);
		if (element)
			return element.getAttribute(name);
		else
			return null;
	};
	
	this._saveECToWindowStorage = function(name, value) {
		window.name = this._replaceInParamStr(window.name, name, value);
	};

	this._saveBestECToWindowStorage = function(name, bestValue) {
		if (bestValue != this.savedValues[this.WINDOW_STORAGE]) {
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
