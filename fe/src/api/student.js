import axios from "./axios_instance"


export function addItem(formData) {
    return axios.post(
        "/student",
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
        "/student",
        {
            params: {
                id,
            }
        }
    )
}


export function updateItem(formData) {
    return axios.patch(
        "/student",
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
        "/student",
        {
            params: {
                id
            }
        }
    )
}

export function listItem(keyword, page, size) {
    return axios.get(
        "/student/list",
        {
            params: {
                keyword,
                page,
                size
            }
        }
    )
}


export function uploadStudent(excelFile) {
    const formData = new FormData();
    console.log(`output-excelFile`,excelFile)
    formData.append("excel", excelFile);
    return axios.post(
        "/student/upload",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    )
}

export function deleteBatch(ids) {
    return axios.delete(
        "/student/delete/batch",
        {
            data: ids,
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}