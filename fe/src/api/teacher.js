import axios from "./axios_instance"


export function addItem(formData) {
    return axios.post(
        "/teacher",
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
        "/teacher",
        {
            params: {
                id,
            }
        }
    )
}


export function updateItem(formData) {
    return axios.patch(
        "/teacher",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    )
}

export function selectItem(id) {
    return axios.get(
        "/teacher",
        {
            params: {
                id
            }
        }
    )
}

export function listItem(keyword, page, size) {
    return axios.get(
        "/teacher/list",
        {
            params: {
                keyword,
                page,
                size
            }
        }
    )
}

export function listAll(keyword, page, pageSize) {
    return axios.get(
        "/teacher/all",
        {
            params: {
                keyword,
                page,
                pageSize
            }
        }
    )
}


