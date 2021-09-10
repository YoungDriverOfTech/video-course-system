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
      return !this.isEmpty();
    },
  
    /**
     * length check
     */
    isLength: function (str, min, max) {
      return $.trim(str).length >= min && $.trim(str).length <= max;
    }
  };