export function shallowCopyObjectWithEmptyValues(obj) {
    if (typeof obj !== 'object' || obj === null) {
        throw new Error('参数必须是一个对象');
    }

    // 创建一个空对象，用于存储拷贝后的属性
    const copiedObj = {};

    // 遍历原始对象的属性
    for (let key in obj) {
        // 检查是否为对象自身的属性（不包括原型链上的属性）
        if (obj.hasOwnProperty(key)) {
            // 获取原始属性的类型
            const type = typeof obj[key];

            // 根据类型赋值为空值
            switch (type) {
                case 'string':
                    copiedObj[key] = '';
                    break;
                case 'number':
                    copiedObj[key] = 0;
                    break;
                case 'boolean':
                    copiedObj[key] = false;
                    break;
                case 'object':
                    copiedObj[key] = null;
                    break;
                default:
                    copiedObj[key] = undefined;
            }
        }
    }

    return copiedObj;
}

