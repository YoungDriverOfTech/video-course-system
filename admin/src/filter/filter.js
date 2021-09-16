// filter for array to find the element according to key
// for example: [{key: "C", value: "charge"}, {key: "F", value: "free"}]
// parameter: key (such as C)
// return: string (such as charge)
let optionKV = (list, key) => {
    if (!list || !key) {
        return '';
    } else {
        let result = '';
        for (let i = 0; i < list.length; i++) {
            if (key === list[i]['key']) {
                result = list[i]['value'];
            }
        }
        return result;
    }
}
export default {
    optionKV
}
