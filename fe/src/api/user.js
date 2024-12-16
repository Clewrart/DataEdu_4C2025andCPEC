import axios from "./axios_instance"

export function login(username, password) {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return axios.post(
        "/user/login",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }

    )
}

export function getUserInfo(token) {
    return axios.get("/user/info", {
        params: {
           token
        }
    })
}

export function addItem(formData) {
    return axios.post(
        "/user",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    )
}

export function deleteItem(id) {
    return axios.delete(
        "/user",
        {
            params: {
                id,
            }
        }
    )
}


export function updateItem(formData) {
    return axios.patch(
        "/user",
        formData,
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}

export function selectItem(id) {
    return axios.get(
        "/user",
        {
            params: {
                id
            }
        }
    )
}

export function listItem(keyword, page, size) {
    return axios.get(
        "/user/list",
        {
            params: {
                keyword,
                page,
                size
            }
        }
    )
}







