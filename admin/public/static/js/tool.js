Tool = {
    /**
     * empty check, return true when param is null or ''
     */
    isEmpty: function (obj) {
      if ((typeof obj == 'string')) {
        return !obj || obj.replace(/\s+/g, "") == ""
      } else {
        return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
      }
    },
  
    /**
     * not empty check
     */
    isNotEmpty: function (obj) {
      return !this.isEmpty(obj);
    },
  
    /**
     * length check
     */
    isLength: function (str, min, max) {
      return $.trim(str).length >= min && $.trim(str).length <= max;
    },

  /**
   * format data
   */
   dateFormat: function (format, date) {
    let result;
    if (!date) {
      date = new Date();
    }
    const option = {
      "y+": date.getFullYear().toString(),        // yarn
      "M+": (date.getMonth() + 1).toString(),     // month
      "d+": date.getDate().toString(),            // day
      "h+": date.getHours().toString(),           // hour
      "m+": date.getMinutes().toString(),         // minute
      "s+": date.getSeconds().toString()          // second
    };
    for (let i in option) {
      result = new RegExp("(" + i + ")").exec(format);
      if (result) {
        format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")))
      }
    }
    return format;
  },

  // remove assigned element from an array
  removeObj: function(array, obj) {
    let index = -1;
    for (let i = 0; i < array.length; i++) {
      if (array[i] === obj) {
        array.splice(i, 1);
        index = i;
        break;
      }
    }
    return index;
  },

  // 10 to 62
  _10to62: function (number) {
    let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
    let radix = chars.length;
    let arr = [];
    do {
      let mod = number % radix;
      number = (number - mod) / radix;
      arr.unshift(chars[mod]);
    } while (number);
    return arr.join('');
  },

  setLoginUser: function(loginUser) {
    SessionStorage.set(SESSION_KEY_LOGIN_USER, loginUser);
  },

  getLoginUser: function() {
    return SessionStorage.get(SESSION_KEY_LOGIN_USER) || {};
  },

  // generate random number
  uuid: function (len, radix) {
    let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    let uuid = [];
    radix = radix || chars.length;
  
    for (let i = 0; i < len; i++) {
      uuid[i] = chars[0 | Math.random() * radix];
    }
  
    return uuid.join('');
  },

  hasResource: function (id) {
    let _this = this;
    let resources = _this.getLoginUser().resources;
    if (_this.isEmpty(resources)) {
      return false;
    }
    for (let i = 0; i < resources.length; i++) {
      if (id === resources[i].id) {
        return true;
      }
    }
    return false;
  }
};
