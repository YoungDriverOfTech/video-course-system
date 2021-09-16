Validator = {
    require: function (value, text) {
        if (Tool.isEmpty(value)) {
            Toast.warning(text + ' is required.');
            return false;
        } else {
            return true;
        }
    },

    length: function (value, text, min, max) {
        if (Tool.isEmpty(value)) {
            return true;
        }
        if (!Tool.isLength(value, min, max)) {
            Toast.warning(text + ' length range is: ' + min + '~' + max);
            return false;
        } else {
            return true;
        }
    }
}