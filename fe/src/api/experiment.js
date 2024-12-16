import axios from "./axios_instance"


export function addItem(formData) {
    return axios.post(
        "/experiment",
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
        "/experiment",
        {
            params: {
                id,
            }
        }
    )
}


export function updateItem(formData) {
    return axios.patch(
        "/experiment",
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
        "/experiment",
        {
            params: {
                id
            }
        }
    )
}

export function listItem(keyword, page, size) {
    return axios.get(
        "/experiment/list",
        {
            params: {
                keyword,
                page,
                size
            }
        }
    )
}

export function listItemByTeacherId(teacherId,keyword,page,size){
     return axios.get(
        "/experiment/listByTeacherId",
        {
            params: {
                teacherId,
                keyword,
                page,
                size
            }
        }
    )
}
export function listItemByStudentId(studentId,keyword){
     return axios.get(
        "/experiment/listByStudentId",
        {
            params: {
                studentId,
                keyword
            }
        }
    )
}


export function getItemById(id) {
    return axios.get("/experiment", {
        params:{
            id
        }
    })
}