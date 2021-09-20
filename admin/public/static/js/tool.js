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
  }
};
