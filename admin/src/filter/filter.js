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
            console.log(object[enums]["key"]);
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
export default {
    optionKV,
    optionKVArray
}
