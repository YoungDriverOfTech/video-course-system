// filter for array to find the element according to key
// for example: [{key: "C", value: "charge"}, {key: "F", value: "free"}]
// parameter: key (such as C)
// return: string (such as charge)
 let optionKV = (object, key) =>  {
    if (!object || !key) {
        return "";
    } else {
        let result = "";
        for(let enums in object){
            if (key === object[enums]["key"]) {
                result = object[enums]["value"];
            }
        }
        return result;
    }
};

let optionKVArray = (list, key) =>  {
    if (!list || !key) {
        return "";
    } else {
        let result = "";
        for (let i = 0; i < list.length; i++) {
            if (key === list[i]["key"]) {
                result = list[i]["value"];
            }
        }
        return result;
    }
};

/**
 * format time
 * @param such as：36000
 * @returns {string} such as：10:00:00
 */
 let formatSecond = (value) => {
    value = value || 0;
    let second = parseInt(value, 10); // second
    let minute = 0; // minute
    let hour = 0; // hour
    if (second > 60) {
        minute = Math.floor(second / 60);
        second = Math.floor(second % 60);
        if (minute > 60) {
            hour = Math.floor(minute / 60);
            minute = Math.floor(minute % 60);
        }
    }
    let result = "" + PrefixInteger(second, 2) + "";
    // 拼上分钟
    result = "" + PrefixInteger(minute, 2) + ":" + result;
    // 拼上小时
    result = "" + PrefixInteger(hour, 2) + ":" + result;
    return result;
};

function PrefixInteger(num, length) {
    return (Array(length).join('0') + num).slice(-length);
}

export default {
    optionKV,
    optionKVArray,
    formatSecond
}
