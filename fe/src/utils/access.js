export function setAccess(access) {
    localStorage.setItem("access",JSON.stringify(access))
}

export function getAccess() {
    return JSON.parse(localStorage.getItem("access"))
}

export function setUserInfo(userinfo) {
    localStorage.setItem("userinfo",JSON.stringify(userinfo))
}
export function getUserInfo() {
    return JSON.parse(localStorage.getItem("userinfo"))
}

export function removeAccess() {
    localStorage.removeItem("access")
}
export function removeUserInfo() {
    localStorage.removeItem("userinfo")
}
export function clearStorage() {
    localStorage.clear()
}