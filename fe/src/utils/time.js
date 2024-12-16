export function getCurrentFormatTime() {
    const now = new Date();

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，需要加1，并确保两位数
    const day = String(now.getDate()).padStart(2, '0');

    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}


export function getCurrentFormatDate() {
    const now = new Date();

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); 
    const day = String(now.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

export function formatTime(timeStr, oldFormat, newFormat) {
  
    function parseTime(timeStr, format) {
        const formatTokens = format.match(/(YYYY|MM|DD|HH|mm|ss)/g);
        const timeTokens = timeStr.match(/(\d{2,4})/g);

        if (!formatTokens || !timeTokens || formatTokens.length !== timeTokens.length) {
            throw new Error("时间字符串和格式不匹配");
        }

        const timeComponents = {};
        formatTokens.forEach((token, index) => {
            timeComponents[token] = parseInt(timeTokens[index], 10);
        });

        return timeComponents;
    }

    // 将解析出来的时间组件转换为 Date 对象
    function createDate(components) {
        return new Date(
            components.YYYY || 1970,
            (components.MM || 1) - 1,
            components.DD || 1,
            components.HH || 0,
            components.mm || 0,
            components.ss || 0
        );
    }

    // 格式化 Date 对象为目标格式的字符串
    function formatDate(date, format) {
        const components = {
            YYYY: date.getFullYear(),
            MM: String(date.getMonth() + 1).padStart(2, '0'),
            DD: String(date.getDate()).padStart(2, '0'),
            HH: String(date.getHours()).padStart(2, '0'),
            mm: String(date.getMinutes()).padStart(2, '0'),
            ss: String(date.getSeconds()).padStart(2, '0')
        };

        return format.replace(/(YYYY|MM|DD|HH|mm|ss)/g, token => components[token]);
    }

    try {
        const parsedComponents = parseTime(timeStr, oldFormat);
        const date = createDate(parsedComponents);
        return formatDate(date, newFormat);
    } catch (error) {
        console.error(error);
        return null;
    }
}

export function formatTimeJSDate(timeStr, format) {
    const date = new Date(timeStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return format.replace(/YYYY/g, year)
        .replace(/MM/g, month)
        .replace(/DD/g, day)
        .replace(/HH/g, hours)
        .replace(/mm/g, minutes)
        .replace(/ss/g, seconds);
}

// 使用示例
console.log(getCurrentFormatTime());
console.log(getCurrentFormatDate());

const oldTimeStr = "2023-06-05 14:30:00";
const oldFormat = "YYYY-MM-DD HH:mm:ss";
const newFormat = "MM/DD/YYYY hh:mm:ss";

const newTimeStr = formatTime(oldTimeStr, oldFormat, newFormat);
console.log(newTimeStr);  // 输出 "06/05/2023 14:30:00"

